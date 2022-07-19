package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "auctions", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuctionEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private CategoryEntity category;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "created_by", nullable = false, length = 36)
    private String createdBy;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "decscriptions")
    private String decscriptions;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "additional")
    private String additional;

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CommentEntity> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<ProductEntity> products = new LinkedHashSet<>();

    @Column(name = "start_price", nullable = false, precision = 50)
    private BigDecimal startPrice;

    @OneToMany(mappedBy = "auction")
    private Set<LikeAuctionEntity> likeAuctions = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AuctionEntity that = (AuctionEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}