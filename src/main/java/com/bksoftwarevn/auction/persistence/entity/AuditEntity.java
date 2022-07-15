package com.bksoftwarevn.auction.persistence.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit", indexes = {
        @Index(name = "idx_user_id", columnList = "actor_user_id"),
        @Index(name = "idx_event_type", columnList = "event_category, event_action, actor_username"),
        @Index(name = "idx_time", columnList = "event_category, actor_username, event_action, event_time")
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AuditEntity {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "status", nullable = false, length = 11)
    private String status;

    @Column(name = "event_time", nullable = false)
    private Instant eventTime;

    @Column(name = "actor_user_id", nullable = false, length = 36)
    private String actorUserId;

    @Column(name = "event_category", nullable = false, length = 35)
    private String eventCategory;

    @Column(name = "actor_username", nullable = false, length = 64)
    private String actorUsername;

    @Column(name = "event_action", nullable = false, length = 35)
    private String eventAction;

    @Column(name = "event_desc", length = 500)
    private String eventDesc;

    @Column(name = "error")
    private String error;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "additional")
    private String additional;

}