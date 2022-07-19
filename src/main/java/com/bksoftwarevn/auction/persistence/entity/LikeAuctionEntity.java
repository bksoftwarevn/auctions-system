package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "like_auction")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LikeAuctionEntity {
    @EmbeddedId
    private LikeAuctionEntityId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @MapsId("auctionId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "auction_id", nullable = false)
    @ToString.Exclude
    private AuctionEntity auction;

    @Column(name = "is_liked")
    private Boolean isLiked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeAuctionEntity that = (LikeAuctionEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}