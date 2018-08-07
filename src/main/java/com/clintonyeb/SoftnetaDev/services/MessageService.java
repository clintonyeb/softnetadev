package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.helpers.Utility;
import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.repositories.IMessageRepository;
import com.sun.syndication.feed.synd.SyndContent;
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
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.util.Date;
import java.util.List;

@Service
public class MessageService implements IMessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private IMessageRepository IMessageRepository;
    @Autowired
    private IFeedService IFeedService;

    @Override
    public List<SyndEntry> getFeedMessages(Feed feed) {
        log.info("Fetching messages for " + feed.getId());

        Reader rd = Utility.makeHTTPRequest(feed.getUrl());

        if (rd != null) {
            try {
                SyndFeed syndFeed = new SyndFeedInput().build(rd);
                return (List<SyndEntry>) syndFeed.getEntries();

            } catch (FeedException e) {
                // invalid xml received
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void addMessages(Feed feed, List<SyndEntry> entries) {
        if(feed == null || entries == null) return;

        for (SyndEntry entry : entries) {
            Message message = new Message();

            SyndContent des = entry.getDescription();
            String description = des != null ? des.getValue() : (entry.getAuthor());

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
            message.setFeed(feed);

            try {
                IMessageRepository.save(message);
                log.info("Saved new message for feed: " + feed.getId());

                // feed has an updated message
                // so updated its last updated date
                IFeedService.updateFeedLastUpdated(feed, new Date());
            } catch (Exception e) {
                // duplicate entries will throw an exception
                // ignore them
                log.info("Ignoring duplicate messages");
            }
        }
    }

    @Override
    public List<Message> getAllMessagesByFeedId(long feedId) {
        return IMessageRepository.findFirst10ByFeedId(feedId, IMessageRepository.MESSAGE_SORT);
    }

    @Override
    public long countMessagesForFeedId(long feedId) {
        return IMessageRepository.countByFeedId(feedId);
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
