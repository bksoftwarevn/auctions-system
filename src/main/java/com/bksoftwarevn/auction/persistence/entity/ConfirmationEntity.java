package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "confirmation", indexes = {
        @Index(name = "idx_user_action_status", columnList = "username, action, status")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ConfirmationEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "username", nullable = false, length = 36)
    private String username;

    @Column(name = "action", nullable = false, length = 35)
    private String action;

    @Column(name = "otp", nullable = false, length = 16)
    private String otp;

    @Column(name = "status", nullable = false, length = 35)
    private String status;

    @Column(name = "expire_date", nullable = false)
    private Instant expireDate;

    @Column(name = "data")
    private String data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConfirmationEntity that = (ConfirmationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}