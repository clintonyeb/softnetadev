package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.helpers.Utility;
import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.models.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class IMessageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IMessageRepository messageRepository;

    /**
     * Test if messages persisted are able to be retrieved
     */
    @Test
    public void findFirst10ByFeedIdTest_1() {

        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        Message mess = new Message();
        mess.setTitle("Good");
        mess.setLink("https://www.15min.lt/rss");
        mess.setFeed(feed);
        entityManager.persist(mess);
        entityManager.flush();

        // when
        List<Message> messages = messageRepository.findFirst10ByFeedId(f.getId(),
                new Sort(Sort.Direction.ASC, "title"));

        // then
        assertThat(messages.iterator().hasNext()).isEqualTo(true);
        assertThat(messages.iterator().next()).isEqualTo(mess);
    }

    /**
     * Test whether all messages are retrieved
     */
    @Test
    public void findFirst10ByFeedIdTest_2() {

        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        int count = 5;

        for (int i = 0; i < count; i++) {
            Message mess = new Message();
            mess.setTitle("Good");
            mess.setLink("https://www.15min.lt/rss_" + i);
            mess.setFeed(feed);
            entityManager.persist(mess);
        }

        entityManager.flush();

        // when
        List<Message> messages = messageRepository.findFirst10ByFeedId(f.getId(), new Sort(Sort.Direction.ASC, "title"));

        // then
        assertThat(messages.size()).isEqualTo(count);
    }

    /**
     * Test whether only 10 items are returned
     */
    @Test
    public void findFirst10ByFeedIdTest_3() {

        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        int count = 20;

        for (int i = 0; i < count; i++) {
            Message mess = new Message();
            mess.setTitle("Good");
            mess.setLink("https://www.15min.lt/rss_" + i);
            mess.setFeed(feed);
            entityManager.persist(mess);
        }

        entityManager.flush();

        // when
        List<Message> messages = messageRepository.findFirst10ByFeedId(f.getId(), new Sort(Sort.Direction.ASC, "title"));

        // then
        assertThat(messages.size()).isEqualTo(10);
    }

    /**
     * Test whether latest items are returned first
     */
    @Test
    public void findFirst10ByFeedIdTest_4() {

        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        int count = 20;

        for (int i = 0; i < count; i++) {
            Message mess = new Message();
            mess.setTitle("Good");
            mess.setLink("https://www.15min.lt/rss_" + i);
            mess.setFeed(feed);
            mess.setPublished(Utility.addDays(new Date(), i));

            entityManager.persist(mess);
        }

        entityManager.flush();

        // when
        List<Message> messages = messageRepository.findFirst10ByFeedId(f.getId(), messageRepository.MESSAGE_SORT);

        Message lastMessage = messages.get(0);
        // then
        assertThat(lastMessage.getLink()).isEqualTo("https://www.15min.lt/rss_19");
    }

    /**
     * Test if count() returns the total number of messages
     * for a feed
     */
    @Test
    public void countByFeedIdTest_1() {

        // given
        Feed feed = new Feed();
        // setting required fields
        feed.setUrl("https://www.15min.lt/rss");
        feed.setFeedName("Test Feed");
        // feed.setId(1L);
        Feed f = entityManager.persist(feed);
        entityManager.flush();

        int count = 20;

        for (int i = 0; i < count; i++) {
            Message mess = new Message();
            mess.setTitle("Good");
            mess.setLink("https://www.15min.lt/rss_" + i);
            mess.setFeed(feed);
            mess.setPublished(new Date());
            entityManager.persist(mess);
        }

        entityManager.flush();

        // when
        int messCount = messageRepository.countByFeedId(f.getId());

        // then
        assertThat(messCount).isEqualTo(count);
    }
}

