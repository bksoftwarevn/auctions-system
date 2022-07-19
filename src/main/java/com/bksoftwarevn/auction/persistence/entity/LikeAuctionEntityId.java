package com.bksoftwarevn.auction.persistence.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikeAuctionEntityId implements Serializable {
    private static final long serialVersionUID = -2434612850974460519L;
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "auction_id", nullable = false, length = 36)
    private String auctionId;

    public String getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(String auctionId) {
        this.auctionId = auctionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LikeAuctionEntityId entity = (LikeAuctionEntityId) o;
        return Objects.equals(this.auctionId, entity.auctionId) &&
                Objects.equals(this.userId, entity.userId);
    }
}