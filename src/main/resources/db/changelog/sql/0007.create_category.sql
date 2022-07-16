-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_category
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'category';

CREATE TABLE `auctions_system`.`category`
(
    `id`           varchar(36)  NOT NULL,
    `name`         varchar(255) NOT NULL,
    `image`        varchar(500) NULL,
    `group_id`     varchar(36)  NOT NULL,
    `descriptions` varchar(500) NULL,
    `additional`   tinytext     NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_name` (`name`),
    UNIQUE INDEX `idx_name_type` (`name`, `group_id`) USING HASH,
    CONSTRAINT `fk_category_group` FOREIGN KEY (`group_id`) REFERENCES `auctions_system`.`groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT

);