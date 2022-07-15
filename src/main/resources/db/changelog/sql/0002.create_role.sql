-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_role
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'role';

CREATE TABLE `auctions_system`.`role`
(
    `id`          int(5)       NOT NULL AUTO_INCREMENT,
    `role`        varchar(36)  NOT NULL,
    `description` varchar(350) NULL,
    `additional`  tinytext         NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_role` (`role`)
);