package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "groups", indexes = {
        @Index(name = "idx_name", columnList = "type", unique = true),
        @Index(name = "idx_type_name", columnList = "name, type", unique = true)
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "additional")
    private String additional;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CategoryEntity> categories = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GroupEntity entity = (GroupEntity) o;
        return id != null && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}