package com.clintonyeb.SoftnetaDev.models;

import com.clintonyeb.SoftnetaDev.helpers.Constants;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id")
    @NotNull
    private Feed feed;

    private String title;

    // NB: the specification does not say that link must be unique
    // Link is even an optional field
    // But for most cases, we can safely assume link is unique for any feed item
    @NotNull
    @Column(unique = true)
    @URL(message = "Invalid URL given")
    private String link;

    private String thumbnail;

    @Column(length = Constants.DESCRIPTION_FIELD_SIZE)
    private String description;

    private Date published;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", feed=" + feed +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", published=" + published +
                '}';
    }
}
