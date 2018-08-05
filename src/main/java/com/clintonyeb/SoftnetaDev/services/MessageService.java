package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.repositories.MessageRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    final int size = 10;

    @Autowired
    private MessageRepository messageRepository;

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

            messageRepository.save(message);
        }
    }

    public Iterable<Message> getAllMessagesByFeedId(long feedId) {
        Sort sort = new Sort(Sort.Direction.ASC, "published");
        Pageable pageable = PageRequest.of(0, size, sort);
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
}
