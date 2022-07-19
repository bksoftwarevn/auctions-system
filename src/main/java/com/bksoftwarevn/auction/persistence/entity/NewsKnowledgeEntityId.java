package com.bksoftwarevn.auction.persistence.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NewsKnowledgeEntityId implements Serializable {
    private static final long serialVersionUID = -8053982311103455153L;
    @Column(name = "news_id", nullable = false, length = 36)
    private String newsId;
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewsKnowledgeEntityId entity = (NewsKnowledgeEntityId) o;
        return Objects.equals(this.newsId, entity.newsId) &&
                Objects.equals(this.userId, entity.userId);
    }
}