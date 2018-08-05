package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.repositories.MessageRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final int SCHEDULER_INTERVAL = 1000 * 60 * 5; // 5 minutes

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private FeedService feedService;

    @Scheduled(fixedDelay = SCHEDULER_INTERVAL)
    public void fetchMessages() {
        log.info("Fetching new messages");

        Iterable<Feed> feeds = feedService.getAllFeeds(10000, 0);

        for (Feed fd : feeds) {
            List entries = getFeedMessages(fd);
            addMessages(fd, entries);
        }
    }

    private List getFeedMessages(Feed feed) {
        log.info("Fetching messages for " + feed.getId());
        Reader rd = UtilityService.makeHTTPRequest(feed.getUrl());

        if (rd != null) {
            try {
                SyndFeed syndFeed = new SyndFeedInput().build(rd);

                return syndFeed.getEntries();

            } catch (FeedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void addMessages(Feed feed, List entries) {
        for (SyndEntry entry : (List<SyndEntry>) entries) {

            String description = entry.getDescription().getValue();

            // some feed items have their images in their descriptions.
            // Parse the image out before applying any formatting.

            String thumbnail = getThumbnaiFromDescription(description);

            if(thumbnail == null || thumbnail.length() < 5) {
                thumbnail = feed.getImageUrl();
            }
            // clean up description
            description = cleanUpDescription(description);

            String title = entry.getTitle();
            // String uri = entry.getUri();
            Date published = entry.getPublishedDate();
            String link = entry.getLink();

            Message message = new Message();
            message.setFeed(feed);
            message.setDescription(description);
            message.setLink(link);
            message.setPublished(published);
            message.setTitle(title);
            message.setThumbnail(thumbnail);

            try {
                messageRepository.save(message);
                log.info("Saved new message for feed: " + feed.getId());
            } catch (Exception e) {
                // duplicate entries will throw an exception
                // ignore them
                log.info("Ignoring duplicate messages");
            }

        }
    }

    public Iterable<Message> getAllMessagesByFeedId(long feedId) {
        Sort sort = new Sort(Sort.Direction.DESC, "published");
        return messageRepository.findFirst10ByFeedId(feedId, sort);
    }

    private String getThumbnaiFromDescription(String description) {
        Document doc = Jsoup.parse(description);
        Elements imgs = doc.getElementsByTag("img");
        return imgs.attr("src");
    }

    private  static String cleanUpDescription(String description){
        // remove all tags
        description = description.replaceAll("\\<.*?\\>", "");

        // make it one line.
        description = description.replaceAll("\\s+"," ");

        // trim string
        description = description.trim();

        return description;
    }

    public long message_size(long feedId){
        return messageRepository.countByFeedId(feedId);
    }
}
