package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.helpers.Utility;
import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.repositories.IFeedRepository;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FeedService implements IFeedService {
    private ExecutorService executorService;

    @Autowired
    private IFeedRepository feedRepository;
    @Autowired
    private MessageService messageService;

    @PostConstruct
    private void create() {
        executorService = Executors.newFixedThreadPool(Constants.FEED_SERVICE_THREAD_POOL_SIZE);
    }

    @PreDestroy
    private void destroy() {
        executorService.shutdown();
    }

    @Override
    public List<Feed> getAllFeeds(int size, int page) {
        Pageable pageable = PageRequest.of(page, size, feedRepository.FEED_SORT);
        return feedRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Feed> getAllFeeds() {
        return (List<Feed>) feedRepository.findAll();
    }

    @Override
    public Feed getFeed(long feedId) {
        Optional<Feed> op = feedRepository.findById(feedId);
        Feed feed;

        if (op.isPresent()) {
            feed = op.get();
            return feed;
        }
        return null;
    }

    @Override
    public Feed addFeed(String url, String feed_name) {
        Feed f = new Feed();

        f.setFeedName(feed_name);
        f.setUrl(url);
        List entries = setFeedInfo(f);

        try {
            Feed feed = feedRepository.save(f);

            // start a new thread to handle adding entries
            // so user is not blocked for too long
            executorService.execute(() -> messageService.addMessages(feed, entries));

            return feed;
        } catch (Exception e) {
            // duplicate entries are going to throw an exception
            // TODO: let the user know the feed already exists / duplicate
        }

        // return old feed, for now
        return null;
    }


    @Override
    public boolean removeFeed(Long feedId) {
        feedRepository.deleteById(feedId);
        return true;
    }

    @Override
    public Feed updateFeedLastUpdated(Feed feed, Date date) {
        if (feed != null) {
            feed.setLastUpdated(date);
            return feedRepository.save(feed);
        }
        return null;
    }

    private List setFeedInfo(Feed feed) {

        Reader rd = Utility.makeHTTPRequest(feed.getUrl());

        if (rd != null) {
            try {
                SyndFeed syndFeed = new SyndFeedInput().build(rd);

                feed.setTitle(syndFeed.getTitle());
                feed.setLastUpdated(syndFeed.getPublishedDate());
                feed.setDescription(syndFeed.getDescription());

                String imgUrl;
                SyndImage image = syndFeed.getImage();

                if (image != null) {
                    imgUrl = image.getUrl();
                    if (imgUrl.length() < 1) {
                        imgUrl = Constants.DEFAULT_IMAGE;
                    }
                } else {
                    imgUrl = Constants.DEFAULT_IMAGE;
                }

                feed.setImageUrl(imgUrl);

                return syndFeed.getEntries();

            } catch (FeedException e) {
                // invalid xml received
                e.printStackTrace();
            }
        }

        return null;
    }
}

