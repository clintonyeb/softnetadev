package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.sun.syndication.feed.synd.SyndEntry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Defines an interface for the MessageService object
 */
@Service
public interface IMessageService {
    List<SyndEntry> getFeedMessages(Feed feed);

    void addMessages(Feed feed, List<SyndEntry> entries);

    List<Message> getAllMessagesByFeedId(long feedId);

    long countMessagesForFeedId(long feedId);
}
