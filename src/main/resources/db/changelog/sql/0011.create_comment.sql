-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_comment
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'comment';

CREATE TABLE `auctions_system`.`comment`
(
    `id`           varchar(36) NOT NULL,
    `auction_id`   varchar(36) NOT NULL,
    `parent_id`    varchar(36) NULL,
    `content`      longtext        NOT NULL,
    `user_id`      varchar(36) NOT NULL,
    `created_date` datetime    NOT NULL,
    `updated_date` datetime    NULL,
    `status`       varchar(50) NULL,
    `additional`   longtext        NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_auction` (`auction_id`) USING BTREE,
    CONSTRAINT `fk_comment_users` FOREIGN KEY (`user_id`) REFERENCES `auctions_system`.`users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_comment_auction` FOREIGN KEY (`auction_id`) REFERENCES `auctions_system`.`auctions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);