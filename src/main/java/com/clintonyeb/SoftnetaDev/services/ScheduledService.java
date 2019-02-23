package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.Application;
import com.clintonyeb.SoftnetaDev.helpers.Constants;
import com.clintonyeb.SoftnetaDev.models.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Background Service responsible for updating feeds.
 * The Service is called automatically at intervals to perform its operations.
 */
@Component
public class ScheduledService {

    @Autowired
    private IFeedService feedService;
    @Autowired
    private MessageService messageService;

    /**
     * This method is scheduled for execution at fixedDea=lay intervals
     */
    @Scheduled(fixedDelay = Constants.SCHEDULER_INTERVAL)
    public void fetchMessages() {
        Application.logger.info("=== Fetching new 'feed items' for feeds ===");

        List<Feed> feeds = feedService.getAllFeeds();

        for (Feed fd : feeds) {
            List entries = messageService.getFeedMessages(fd);
            messageService.addMessages(fd, entries);
        }

        Application.logger.info("=== Scheduler done fetching feeds. Taking a nap now. See ya. ===");
    }
}
