-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_like_auction
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'like_auction';

CREATE TABLE `like_auction`
(
    `user_id`    varchar(36) COLLATE utf8_unicode_ci NOT NULL,
    `auction_id` varchar(36) COLLATE utf8_unicode_ci NOT NULL,
    `is_liked`   tinyint(1) DEFAULT 0,
    PRIMARY KEY (`user_id`, `auction_id`),
    KEY `fk_like_auction` (`auction_id`),
    CONSTRAINT `fk_like_auction` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`),
    CONSTRAINT `fk_like_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;