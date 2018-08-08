package com.clintonyeb.SoftnetaDev.repositories;

import com.clintonyeb.SoftnetaDev.helpers.Constants;
import com.clintonyeb.SoftnetaDev.models.Feed;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeedRepository extends PagingAndSortingRepository<Feed, Long> {
    Sort FEED_SORT = new Sort(Sort.Direction.ASC, Constants.FEED_SORT_PROPERTY);
}
