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
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    public List getFeedMessages(Feed feed) {
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
            Message message = new Message();

            String description = entry.getDescription().getValue();

            // some feed items have their images in their descriptions.
            // Parse the image out before applying any formatting.
            String thumbnail = getThumbnailFromDescription(description);
            if (thumbnail == null || thumbnail.length() < 1) {
                thumbnail = feed.getImageUrl();
            }

            // clean up description
            description = cleanUpDescription(description);

            message.setDescription(description);
            message.setThumbnail(thumbnail);
            message.setTitle(entry.getTitle());
            message.setPublished(entry.getPublishedDate());
            message.setLink(entry.getLink());

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
        Sort sort = new Sort(Sort.Direction.DESC, Constants.MESSAGE_SORT_PROPERTY);
        return messageRepository.findFirst10ByFeedId(feedId, sort);
    }

    public long countMessagesForFeedId(long feedId) {
        return messageRepository.countByFeedId(feedId);
    }

    private String getThumbnailFromDescription(String description) {
        Document doc = Jsoup.parse(description);
        Elements imgs = doc.getElementsByTag("img");
        return imgs.attr("src");
    }

    private String cleanUpDescription(String description) {
        // remove all tags
        description = description.replaceAll("\\<.*?\\>", "");

        // make it one line.
        description = description.replaceAll("\\s+"," ");

        // trim string
        description = description.trim();

        return description;
    }
}
