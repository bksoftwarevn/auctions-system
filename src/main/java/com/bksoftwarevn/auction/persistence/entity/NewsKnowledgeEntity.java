package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "news_knowledge", indexes = {
        @Index(name = "idx", columnList = "news_id, user_id, is_read")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NewsKnowledgeEntity {
    @EmbeddedId
    private NewsKnowledgeEntityId id;

    @MapsId("newsId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "news_id", nullable = false)
    @ToString.Exclude
    private NewsEntity news;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "is_read")
    private Boolean isRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewsKnowledgeEntity that = (NewsKnowledgeEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}