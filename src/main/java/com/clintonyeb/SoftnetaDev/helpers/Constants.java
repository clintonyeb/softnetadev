package com.clintonyeb.SoftnetaDev.helpers;

/**
 * Declare Global constants for the application
 */
public interface Constants {

    // default image for a feed if image is absent
    String DEFAULT_IMAGE = "/images/logo.png";

    // Number of threads to use for fetching feeds
    int FEED_SERVICE_THREAD_POOL_SIZE = 10;

    // Property to sort feed items with
    String MESSAGE_SORT_PROPERTY = "published";

    // Property to sort feeds with
    String FEED_SORT_PROPERTY = "lastUpdated";

    // Internal to fetch new feeds or checking for updates
    int SCHEDULER_INTERVAL = 1000 * 60 * 5; // 5 minutes

    // Since descriptions could be any size, I decide to specify the column width for the table here for easy amendment.
    int DESCRIPTION_FIELD_SIZE = 5000;
}
