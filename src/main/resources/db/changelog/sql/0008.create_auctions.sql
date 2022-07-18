-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_auctions
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'auctions';

CREATE TABLE `auctions_system`.`auctions`
(
    `id`           varchar(36)  NOT NULL,
    `category_id`  varchar(36)  NOT NULL,
    `title`        varchar(500) NOT NULL,
    `start_date`   datetime(0)  NOT NULL,
    `end_date`     datetime(0)  NOT NULL,
    `created_date` datetime(0)  NOT NULL,
    `created_by`   varchar(36)  NOT NULL,
    `user_id`      varchar(36)  NOT NULL,
    `descriptions` tinytext     NULL,
    `status`       varchar(50)  NOT NULL,
    `reason`       varchar(500) NULL,
    `additional`   tinytext     NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_status` (`status`) USING HASH,
    INDEX `idx_user_id` (`user_id`) USING BTREE,
    CONSTRAINT `fk_auction_user` FOREIGN KEY (`user_id`) REFERENCES `auctions_system`.`users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_auction_category` FOREIGN KEY (`category_id`) REFERENCES `auctions_system`.`category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);