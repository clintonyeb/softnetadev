package com.clintonyeb.SoftnetaDev.helpers;

public interface Constants {
    String DEFAULT_IMAGE = "/images/logo.png";
    int FEED_SERVICE_THREAD_POOL_SIZE = 10;
    String MESSAGE_SORT_PROPERTY = "published";
    String FEED_SORT_PROPERTY = "lastUpdated";
    int SCHEDULER_INTERVAL = 1000 * 60 * 5; // 5 minutes
    int DESCRIPTION_FIELD_SIZE = 5000;
}
