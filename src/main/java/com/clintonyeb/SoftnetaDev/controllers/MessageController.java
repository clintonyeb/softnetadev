package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.services.IFeedService;
import com.clintonyeb.SoftnetaDev.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Handles requests to the message model of the application.
 * Makes use of two services: FeedService and MessageService.
 * Currently handles getting list of feed messages.
 */
@Controller
public class MessageController {
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IFeedService feedService;

    /**
     * Get a list of messages (feed items) for a feed
     * @param size Limit of messages to return
     * @param feedId The id of the feed to get items for
     * @param model
     * @return List of feed items
     */
    @GetMapping("/messages")
    public String get_feed_messages(@RequestParam(name = "size", required = false, defaultValue = "10") String size,
                                    @RequestParam(name = "feed_id") long feedId,
                                    Model model) {
        Feed feed = feedService.getFeed(feedId);

        List<Message> messages = messageService.getAllMessagesByFeedId(feedId);

        model.addAttribute("feed", feed);
        model.addAttribute("messages", messages);
        model.addAttribute("article_count", messageService.countMessagesForFeedId(feedId));

        return "messages";
    }

}
