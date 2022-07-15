package com.bksoftwarevn.auction.persistence.entity;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class UserRoleEntityId implements Serializable {
    private static final long serialVersionUID = 2972427870241169793L;
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRoleEntityId that = (UserRoleEntityId) o;
        return userId != null && Objects.equals(userId, that.userId)
                && roleId != null && Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}