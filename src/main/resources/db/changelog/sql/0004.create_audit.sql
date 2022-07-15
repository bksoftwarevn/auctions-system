-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_audit
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'audit';

CREATE TABLE `auctions_system`.`audit`
(
    `id`             varchar(36)  NOT NULL,
    `status`         varchar(11)  NOT NULL,
    `event_time`     datetime(0)  NOT NULL,
    `actor_user_id`  varchar(36)  NOT NULL,
    `event_category` varchar(35)  NOT NULL,
    `actor_username` varchar(64)  NOT NULL,
    `event_action`   varchar(35)  NOT NULL,
    `event_desc`     varchar(500) NULL,
    `error`          varchar(255) NULL,
    `metadata`       tinytext         NULL,
    `additional`     tinytext         NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_event_type` (`event_category`, `event_action`, `actor_username`) USING BTREE,
    INDEX `idx_user_id` (`actor_user_id`) USING BTREE,
    INDEX `idx_time` (`event_category`, `actor_username`, `event_action`, `event_time`) USING BTREE
);