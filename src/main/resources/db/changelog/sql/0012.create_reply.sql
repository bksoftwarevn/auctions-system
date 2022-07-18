-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_reply
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'reply';

CREATE TABLE `auctions_system`.`reply`
(
    `id`           varchar(36) NOT NULL,
    `content`      longtext        NOT NULL,
    `created_date` datetime    NOT NULL,
    `updated_date` datetime    NULL,
    `status`       varchar(50) NULL,
    `user_id`      varchar(36) NOT NULL,
    `comment_id`   varchar(36) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_reply_user` FOREIGN KEY (`user_id`) REFERENCES `auctions_system`.`users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_reply_comment` FOREIGN KEY (`comment_id`) REFERENCES `auctions_system`.`comment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);