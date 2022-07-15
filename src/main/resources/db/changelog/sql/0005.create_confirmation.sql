-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_confirmation
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'confirmation';

CREATE TABLE `auctions_system`.`confirmation`
(
    `id`          varchar(36)     NOT NULL,
    `username`     varchar(36) NOT NULL,
    `action`      varchar(35) NOT NULL,
    `otp`         varchar(16) NOT NULL,
    `status`      varchar(35) NOT NULL DEFAULT 'Unknown',
    `expire_date` datetime    NOT NULL,
    `data`        tinytext        NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_user_action_status` (`username`, `action`, `status`)
);