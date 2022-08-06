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
@Table(name = "product", indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_brand", columnList = "brand_id")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ProductEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "start_price", nullable = false, precision = 30)
    private BigDecimal startPrice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    @ToString.Exclude
    private BrandEntity brand;

    @Column(name = "descriptions", nullable = false)
    private String descriptions;

    @Column(name = "main_image", length = 500)
    private String mainImage;

    @Column(name = "images")
    private String images;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "auction_id", nullable = false)
    @ToString.Exclude
    private AuctionEntity auction;

    @Column(name = "series")
    private String series;

    @Column(name = "buyer", length = 36)
    private String buyer;

    @Column(name = "max_bid", precision = 30)
    private BigDecimal maxBid;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private Set<BidEntity> bids = new LinkedHashSet<>();

    @Lob
    @Column(name = "accepted_info", columnDefinition = "LONGTEXT")
    private String acceptedInfo;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductEntity that = (ProductEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}