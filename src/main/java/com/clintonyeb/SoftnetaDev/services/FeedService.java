package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import com.clintonyeb.SoftnetaDev.repositories.FeedRepository;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FeedService {

    private final String DEFAULT_IMAGE = "/images/logo.png";

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private MessageService messageService;

    public Iterable<Feed> getAllFeeds(int size, int page) {
        Sort sort = new Sort(Sort.Direction.ASC, "lastUpdated");
        Pageable pageable = PageRequest.of(page, size, sort);
        return feedRepository.findAll(pageable);
    }

    public Feed getFeed(long feedId) {
        Optional<Feed> op = feedRepository.findById(feedId);
        Feed feed;

        if (op.isPresent()) {
            feed = op.get();
            return feed;
        }
        return null;
    }

    public Feed addFeed(String url, String feed_name) {
        Feed f = new Feed();

        List entries = getFeedInformation(f, url);
        f.setFeedName(feed_name);
        f.setUrl(url);

        try{
            Feed feed = feedRepository.save(f);

            // start a new thread to handle adding entries
            // so user is not blocked for too long
            executorService.execute(() -> messageService.addMessages(feed, entries));

            return feed;
        } catch (Exception e){
            // duplicate entries are going to throw an exception
            // what to do?
            // pass
        }

        // return old feed
        return f;
    }

    public Iterable<Message> getFeedMessages(Long feedId, int size) {
        Feed feed = getFeed(feedId);

        if(feed != null) {
            return messageService.getAllMessagesByFeedId(feed.getId());
        }

        return null;
    }

    public boolean removeFeed(Long feedId) {
        feedRepository.deleteById(feedId);
        return true;
    }

    private List getFeedInformation(Feed feed, String url) {
        Reader rd = makeHTTPRequest(url);

        if (rd != null) {
            try {
                SyndFeed syndFeed = new SyndFeedInput().build(rd);

                feed.setTitle(syndFeed.getTitle());
                feed.setLastUpdated(syndFeed.getPublishedDate());
                feed.setDescription(syndFeed.getDescription());

                String imgUrl = null;
                SyndImage image = syndFeed.getImage();

                if(image != null) {
                     imgUrl = image.getUrl();
                     if(imgUrl.length() < 5) {
                         imgUrl = DEFAULT_IMAGE;
                     }
                } else {
                    imgUrl = DEFAULT_IMAGE;
                }

                feed.setImageUrl(imgUrl);

                return syndFeed.getEntries();

            } catch (FeedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private Reader makeHTTPRequest(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        // request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            int status = response.getStatusLine().getStatusCode();

            BufferedReader rd;

            if (status == HttpStatus.SC_OK) {
                rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                return rd;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public long feed_size(){
        return feedRepository.count();
    }


}

