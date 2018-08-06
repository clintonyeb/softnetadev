package com.clintonyeb.SoftnetaDev.models;

import com.clintonyeb.SoftnetaDev.services.Constants;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class Feed {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    // According to RSS Specification (https://validator.w3.org/feed/docs/rss2.html)
    // Uri is guaranteed to be unique for every feed
    // We can use it to remove duplicate feeds
    @NotNull
    @Column(unique=true)
    @URL(message = "Invalid URL given")
    private String url;

    private String title;

    private Date lastUpdated;

    private String imageUrl;

    @Column(length = Constants.DESCRIPTION_FIELD_SIZE)
    private String description;

    @NotNull
    private String feedName;

    @OneToMany(targetEntity=Message.class, mappedBy="feed", fetch=FetchType.LAZY)
    private Set<Message> messages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", lastUpdated=" + lastUpdated +
                ", feedName='" + feedName + '\'' +
                '}';
    }
}
