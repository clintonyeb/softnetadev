package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Defines an interface for the FeedService object
 */
@Service
public interface IFeedService {
    List<Feed> getAllFeeds(int size, int page);

    List<Feed> getAllFeeds();

    Feed getFeed(long feedId);

    Feed addFeed(String url, String feed_name);

    boolean removeFeed(Long feedId);

    Feed updateFeedLastUpdated(Feed feed, Date date);
}
