package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.models.Feed;
import com.clintonyeb.SoftnetaDev.services.Constants;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {
    Sort FEED_SORT = new Sort(Sort.Direction.ASC, Constants.FEED_SORT_PROPERTY);
}