package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.services.FeedService;
import com.clintonyeb.SoftnetaDev.services.MessageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedController {
    @Autowired
    private FeedService feedService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String home(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "redirect:/feeds";
    }

    @GetMapping("/feeds")
    public String get_feeds(@RequestParam(name="size", required=false, defaultValue="20") String size,
                                             @RequestParam(name="page", required=false, defaultValue="0") String page,
                                             Model model) {
        int sizeInt = Integer.parseInt(size);
        int pageInt = Integer.parseInt(page);

        Iterable<Feed> feeds = feedService.getAllFeeds(sizeInt, pageInt);
        model.addAttribute("items", feeds);

        return "feeds";
    }

    @PostMapping("/feeds")
    public @ResponseBody String post_feed(@RequestParam(name="url") String url,
                 @RequestParam(name="feed_name") String feedName,
                 Model model) {
        Feed feed = feedService.addFeed(url, feedName);
        if(feed != null) {
            Gson gson = new Gson();
            return gson.toJson(feed);
        }
        return "Error saving feed";
    }

    @PostMapping("/delete_feed")
    public String delete_feed(@RequestParam(name="feed_id") String feed_id,
                                          Model model) {
        boolean status = feedService.removeFeed(Long.parseLong(feed_id));
        if(status) {
            return "redirect:/feeds";
        }

        // TODO: set a message before returning error
        return "error";
    }
}
