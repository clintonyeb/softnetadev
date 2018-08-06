package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.repositories.FeedRepository;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FeedService {

    private ExecutorService executorService =
            Executors.newFixedThreadPool(Constants.FEED_SERVICE_THREAD_POOL_SIZE);

    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private MessageService messageService;

    public Iterable<Feed> getAllFeeds(int size, int page) {
        Sort sort = new Sort(Sort.Direction.ASC, Constants.FEED_SORT_PROPERTY);
        Pageable pageable = PageRequest.of(page, size, sort);
        return feedRepository.findAll(pageable);
    }

    public Iterable<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }

    public Feed getFeed(long feedId) {
        Optional<Feed> op = feedRepository.findById(feedId);
        Feed feed;

        if (op.isPresent()) {
            feed = op.get();
            return feed;
        }
        return null;
    }

    public Feed addFeed(String url, String feed_name) {
        Feed f = new Feed();

        List entries = getFeedInformation(f, url);
        f.setFeedName(feed_name);
        f.setUrl(url);

        try{
            Feed feed = feedRepository.save(f);

            // start a new thread to handle adding entries
            // so user is not blocked for too long
            executorService.execute(() -> messageService.addMessages(feed, entries));

            return feed;
        } catch (Exception e){
            // duplicate entries are going to throw an exception
            // TODO: let the user know the feed already exists
        }

        // return old feed, for now
        return f;
    }

    public Iterable<Message> getFeedMessages(Long feedId, int size) {
        Feed feed = getFeed(feedId);

        if(feed != null) {
            return messageService.getAllMessagesByFeedId(feed.getId());
        }

        return null;
    }

    public boolean removeFeed(Long feedId) {
        feedRepository.deleteById(feedId);
        return true;
    }

    private List getFeedInformation(Feed feed, String url) {
        Reader rd = UtilityService.makeHTTPRequest(url);

        if (rd != null) {
            try {
                SyndFeed syndFeed = new SyndFeedInput().build(rd);

                feed.setTitle(syndFeed.getTitle());
                feed.setLastUpdated(syndFeed.getPublishedDate());
                feed.setDescription(syndFeed.getDescription());

                String imgUrl;
                SyndImage image = syndFeed.getImage();

                if(image != null) {
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
                e.printStackTrace();
            }
        }

        return null;
    }
}

