package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.helpers.Constants;
import com.clintonyeb.SoftnetaDev.models.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Extends the Message Model
 */
@Repository
public interface IMessageRepository extends PagingAndSortingRepository<Message, Long> {

    String countByFeedId = "SELECT COUNT(id) FROM Message m WHERE m.feed.id = ?1";

    // Adds a sorting capability to the message model
    Sort MESSAGE_SORT = new Sort(Sort.Direction.DESC, Constants.MESSAGE_SORT_PROPERTY);

    @Query(countByFeedId)
    Integer countByFeedId(Long feed_id);

    List<Message> findFirst10ByFeedId(@Param("feedId") long feedId, Sort sort);
}
