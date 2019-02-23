package com.clintonyeb.SoftnetaDev.controllers;

import com.clintonyeb.SoftnetaDev.Application;
import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.services.IFeedService;
import com.clintonyeb.SoftnetaDev.services.IMessageService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests to the feed model of the application.
 * Makes use of one service: FeedService.
 * Currently handles getting list of feeds, creating a feed, deleting a feed.
 */
@Controller
public class FeedController {

    // Needed to execute requests relating to feeds.
    @Autowired
    private IFeedService feedService;

    // Make '/feeds' the default route by simply calling the get_feeds method.
    // In earlier implementation, we were redirecting the route. This has been removed for simplicity sake.
    // by using: return "redirect:/feeds"; To redirect url
    @GetMapping("/")
    public String home(Model model) {
        return getFeeds(20, 0, model);
    }

    /**
     * Get the list of all feeds.
     * Invokes the FeedService object to get the list of all feeds.
     * @param size Number of feeds to limit to
     * @param page Current page for pagination
     * @param model
     * @return a view object with the name feed (check src/main/webapp/WEB-INF/views)
     */
    @GetMapping("/feeds")
    public String getFeeds(@RequestParam(name = "size", required = false, defaultValue = "20") int size,
                            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                            Model model) {
        Application.logger.info("Executing request to fetch all feeds");
        model.addAttribute("items", feedService.getAllFeeds(size, page));
        return "feeds";
    }

    /**
     * Creates a new feed
     * Invokes the FeedService object to add a new feed
     * NB: This method returns a raw json-string, as it is expected to be called from an ajax request
     * @param url The url to the XML RSS Feed
     * @param feedName A display name for the feed
     * @param model
     * @return The created feed or Error message
     */
    @PostMapping("/feeds")
    public @ResponseBody
    String postFeed(@RequestParam(name = "url") String url,
                     @RequestParam(name = "feed_name") String feedName,
                     Model model) {
        Application.logger.info("Executing request to create a new feed.");

        Feed feed = feedService.addFeed(url, feedName);

        if (feed != null) {
            Gson gson = new Gson();
            return gson.toJson(feed);
        }

        Application.logger.info("Error creating a new feed.");
        return "Error saving feed";
    }

    /**
     * Deletes a feed
     * Invokes the FeedService object to delete a feed
     * @param feedId The Id of the feed to delete
     * @param model
     * @return Redirect to all feeds or an error message
     */
    @PostMapping("/delete_feed")
    public String deleteFeed(@RequestParam(name = "feed_id") Long feedId,
                              Model model) {
        Application.logger.info("Executing request to delete a feed.");

        boolean status = feedService.removeFeed(feedId);

        // if feed was successfully deleted, redirect to all feeds
        if (status) {
            return "redirect:/feeds";
        }

        model.addAttribute("message", "There was an error deleting feed.");
        Application.logger.info("Error deleting a feed.");

        return "error";
    }
}
