package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

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

    @Column(name = "start_price", nullable = false, length = 50)
    private String startPrice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @Column(name = "descriptions", nullable = false)
    private String descriptions;

    @Column(name = "main_image", length = 500)
    private String mainImage;

    @Column(name = "images")
    private String images;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "auction_id", nullable = false)
    private AuctionEntity auction;

    @Column(name = "series")
    private String series;

    @Column(name = "buyer", length = 36)
    private String buyer;

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