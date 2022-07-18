package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category", indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_name_type", columnList = "name, group_id", unique = true)
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", length = 500)
    private String image;


    @Column(name = "descriptions", length = 500)
    private String descriptions;

    @Column(name = "additional")
    private String additional;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private GroupEntity group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CategoryEntity that = (CategoryEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}