package com.bksoftwarevn.auction.persistence.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class NotificationEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "receiver", nullable = false, length = 36)
    private String receiver;

    @Column(name = "is_read")
    private Boolean isRead;

    @Lob
    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "event_id", nullable = false, length = 36)
    private String eventId;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "action_category", nullable = false, length = 50)
    private String actionCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        NotificationEntity that = (NotificationEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}