package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.services.FeedService;
import com.clintonyeb.SoftnetaDev.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private FeedService feedService;

    @GetMapping("/messages")
    public String get_feed_messages(@RequestParam(name = "size", required = false, defaultValue = "10") String size,
                                    @RequestParam(name = "feed_id") String feed_id,
                                    Model model) {
        Long feedId = Long.parseLong(feed_id);
        Feed feed = feedService.getFeed(feedId);

        Iterable<Message> messages = feedService.getFeedMessages(feedId, Integer.parseInt(size));

        model.addAttribute("feed", feed);
        model.addAttribute("messages", messages);
        model.addAttribute("article_count", messageService.countMessagesForFeedId(feed.getId()));

        return "messages";
    }

}
