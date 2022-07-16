-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_groups
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'groups';

CREATE TABLE `auctions_system`.`groups`
(
    `id`          varchar(36)  NOT NULL,
    `name`        varchar(255) NOT NULL,
    `type`        varchar(255) NOT NULL,
    `descriptions` varchar(255) NULL,
    `additional`  tinytext     NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idx_name` (`type`) USING BTREE,
    UNIQUE INDEX `idx_type_name` (`name`, `type`) USING BTREE
);