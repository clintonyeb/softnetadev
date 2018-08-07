package com.clintonyeb.SoftnetaDev.services;

import com.clintonyeb.SoftnetaDev.helpers.Utility;
import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.repositories.IFeedRepository;
import com.clintonyeb.SoftnetaDev.repositories.IMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration
@DataJpaTest
@ActiveProfiles("test")
public class FeedServiceTest {

    @Autowired
    IFeedService feedService;
    @Autowired
    MessageService messageService;
    @Autowired
    private IFeedRepository feedRepository;
    @Autowired
    private IMessageRepository messageRepository
            ;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getAllFeedTest_1() {
        // when
        List<Feed> feeds = feedService.getAllFeeds();

        // then
        assertThat(feeds.size()).isEqualTo(0);
    }

    @Test
    public void getAllFeedTest_2() {
        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        // when
        List<Feed> feeds = feedService.getAllFeeds();

        // then
        assertThat(feeds.size()).isEqualTo(1);
    }

    @Test
    public void getAllFeedTest_3() {
        // given
        int count = 5;

        for (int i = 0; i < count; i++) {
            Feed feed = new Feed();
            // setting required fields
            feed.setUrl("https://www.15min.lt/rss_" + i);
            feed.setFeedName("Test Feed_" + i);
            // feed.setId(1L);
            entityManager.persist(feed);
        }

        entityManager.flush();

        // when
        List<Feed> feeds = feedService.getAllFeeds();

        // then
        assertThat(feeds.size()).isEqualTo(count);
    }

    @Test
    public void getAllFeedTest_4() {
        // given
        int count = 5;

        for (int i = 0; i < count; i++) {
            Feed feed = new Feed();
            // setting required fields
            feed.setUrl("https://www.15min.lt/rss_" + i);
            feed.setFeedName("Test Feed_" + i);
            // feed.setId(1L);
            entityManager.persist(feed);
        }

        entityManager.flush();

        int n = 3;
        // when
        List<Feed> feeds = feedService.getAllFeeds(n, 0);

        // then
        assertThat(feeds.size()).isEqualTo(n);
    }

    @Test
    public void getAllFeedTest_5() {
        // given
        int count = 5;

        for (int i = 0; i < count; i++) {
            Feed feed = new Feed();
            // setting required fields
            feed.setUrl("https://www.15min.lt/rss_" + i);
            feed.setFeedName("Test Feed_" + i);
            // feed.setId(1L);
            entityManager.persist(feed);
        }

        entityManager.flush();

        int n = 3;
        // when
        List<Feed> feeds = feedService.getAllFeeds(n, 1);

        // then
        assertThat(feeds.size()).isEqualTo(count - n);
    }

    @Test
    public void getFeedTest_1_with_id_that_exists() {
        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        Feed testFeed = feedService.getFeed(f.getId());

        // then
        assertThat(testFeed).isEqualTo(f);
    }

    @Test
    public void getFeedTest_1_with_id_that_not_exists() {
        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        Feed testFeed = feedService.getFeed(12);

        // then
        assertThat(testFeed).isEqualTo(null);
    }

    @Test
    public void getFeedTest_1_with_invalid_id() {
        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        Feed testFeed = feedService.getFeed(-1);

        // then
        assertThat(testFeed).isEqualTo(null);
    }

    @Test
    public void addFeed_1() {
        // given
        int count = 5;

        for (int i = 0; i < count; i++) {
            feedService.addFeed("https://www.15min.lt/rss_" + i, "Test Feed_" + i);
        }

        entityManager.flush();

        int n = 3;
        // when
        List<Feed> feeds = feedService.getAllFeeds(n, 0);

        // then
        assertThat(feeds.size()).isEqualTo(n);
    }

    @Test
    public void addFeedTest_with_duplicate_url() {
        /// given
        Feed feed = new Feed();
        feed.setUrl("Test");
        feed.setFeedName("Test Feed");
        Feed f = feedRepository.save(feed);

        Feed feed2 = new Feed();
        feed.setUrl("Test");
        feed.setFeedName("Test Feed");
        Feed f2 = feedRepository.save(feed2);

        // then
        assertThat(f).isNotEqualTo(f2);
    }

    @Test
    public void removeFeedTest() {
        /// given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        feedService.removeFeed(f.getId());

        // when
        List<Feed> feeds = feedService.getAllFeeds();

        // then
        assertThat(feeds.size()).isEqualTo(0);
    }

    @Test(expected = Exception.class)
    public void removeFeedTest_invalid_id() {
        // when
        assertThat(feedService.removeFeed(-1L));
    }

    @Test
    public void updateFeedTest() {
        /// given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        feed.setLastUpdated(new Date());
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        // when
        Date newDate = Utility.addDays(new Date(), 1);
        Feed updatedFeed = feedService.updateFeedLastUpdated(f, newDate);

        // then
        assertThat(updatedFeed.getLastUpdated()).isEqualTo(newDate);
    }

    @TestConfiguration
    static class FeedServiceTestContextConfiguration {

        @Bean
        public IFeedService feedService() {
            return new FeedService();
        }

        @Bean
        public MessageService messageService() {
            return new MessageService();
        }

        @Bean
        public IFeedRepository feedRepository() {
            return Mockito.mock(IFeedRepository.class);
        }

        @Bean
        public IMessageRepository messageRepository() {
            return Mockito.mock(IMessageRepository.class);
        }
    }
}
