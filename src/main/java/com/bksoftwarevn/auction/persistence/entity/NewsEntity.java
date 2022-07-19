package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "news")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NewsEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "descriptions", nullable = false)
    private String descriptions;


    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<NewsKnowledgeEntity> newsKnowledges = new LinkedHashSet<>();

    @Column(name = "created_by", nullable = false, length = 500)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NewsEntity that = (NewsEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}