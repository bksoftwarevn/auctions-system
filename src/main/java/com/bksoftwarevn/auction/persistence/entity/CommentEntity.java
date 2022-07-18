package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "comment", indexes = {
        @Index(name = "fk_comment_comment", columnList = "parent_id"),
        @Index(name = "idx_auction", columnList = "auction_id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CommentEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "auction_id", nullable = false)
    @ToString.Exclude
    private AuctionEntity auction;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "status", length = 50)
    private String status;

    @Lob
    @Column(name = "additional", columnDefinition = "LONGTEXT")
    private String additional;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @OneToMany(mappedBy = "comment")
    @ToString.Exclude
    private Set<ReplyEntity> replies = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CommentEntity that = (CommentEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}