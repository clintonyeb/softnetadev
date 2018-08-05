package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.models.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    public Iterable<Message> findAllByFeedId(@Param("feedId") long feedId);
    public Iterable<Message> findFirst10ByFeedId(@Param("feedId") long feedId, Sort sort);
}
