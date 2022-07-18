package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "brand", indexes = {
        @Index(name = "idx_name", columnList = "name", unique = true)
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BrandEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "info")
    private String info;

    @Column(name = "additional")
    private String additional;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BrandEntity that = (BrandEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}