package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "bid")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BidEntity {
    @EmbeddedId
    private BidEntityId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private ProductEntity product;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "price", nullable = false, precision = 30)
    private BigDecimal price;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total", nullable = false)
    private Integer total;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BidEntity bidEntity = (BidEntity) o;
        return id != null && Objects.equals(id, bidEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}