package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.helpers.Constants;
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

/**
 * Implements functionalities for the FeedService object.
 */
@Service
public class FeedService implements IFeedService {
    private ExecutorService executorService;

    @Autowired
    private IFeedRepository IFeedRepository;
    @Autowired
    private IMessageService IMessageService;

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
        Pageable pageable = PageRequest.of(page, size, IFeedRepository.FEED_SORT);
        return IFeedRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Feed> getAllFeeds() {
        return (List<Feed>) IFeedRepository.findAll();
    }

    @Override
    public Feed getFeed(long feedId) {
        Optional<Feed> op = IFeedRepository.findById(feedId);

        return op.orElse(null);

    }

    @Override
    public Feed addFeed(String feedURL, String feedName) {
        Feed f = new Feed();

        f.setFeedName(feedName);
        f.setUrl(feedURL);

        try {
            Feed feed = IFeedRepository.save(f);

            // start a new thread to handle adding entries
            // so user is not blocked for too long
            List entries = setFeedInfo(f);
            executorService.execute(() -> IMessageService.addMessages(feed, entries));

            return feed;
        } catch (Exception e) {
            // duplicate entries are going to throw an exception
            // TODO: let the user know the feed already exists / duplicate
        }

        return null;
    }


    @Override
    public boolean removeFeed(Long feedId) {
        IFeedRepository.deleteById(feedId);
        return true;
    }

    @Override
    public Feed updateFeedLastUpdated(Feed feed, Date date) {
        if (feed != null) {
            feed.setLastUpdated(date);
            return IFeedRepository.save(feed);
        }
        return null;
    }

    /**
     * Populates Feed Information from an HTTP call
     * @param feed the feed to be populated
     * @return
     */
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

