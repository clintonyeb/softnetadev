package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.helpers.Constants;
import com.clintonyeb.SoftnetaDev.models.Feed;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Extends the Feed Model
 */
@Repository
public interface IFeedRepository extends PagingAndSortingRepository<Feed, Long> {

    // Adds a sorting capability to the feed model
    Sort FEED_SORT = new Sort(Sort.Direction.ASC, Constants.FEED_SORT_PROPERTY);
}
