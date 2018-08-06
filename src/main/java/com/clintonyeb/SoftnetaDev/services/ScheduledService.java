package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    private IFeedService IFeedService;
    @Autowired
    private MessageService messageService;

    @Scheduled(fixedDelay = Constants.SCHEDULER_INTERVAL)
    public void fetchMessages() {
        log.info("Fetching new messages");

        List<Feed> feeds = IFeedService.getAllFeeds();

        for (Feed fd : feeds) {
            List entries = messageService.getFeedMessages(fd);
            if (entries != null)
                messageService.addMessages(fd, entries);
        }
    }
}
