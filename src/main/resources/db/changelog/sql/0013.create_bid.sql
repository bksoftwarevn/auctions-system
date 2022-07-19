-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_bid
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'bid';

CREATE TABLE `bid`
(
    `product_id`   varchar(36) COLLATE utf8_unicode_ci  NOT NULL,
    `user_id`      varchar(36) COLLATE utf8_unicode_ci  NOT NULL,
    `price`        decimal(30, 0)                       NOT NULL,
    `created_date` datetime                             NOT NULL,
    `updated_date` datetime DEFAULT NULL,
    `status`       varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `total`        int(10)                              NOT NULL,
    PRIMARY KEY (`product_id`, `user_id`) USING BTREE,
    UNIQUE KEY `idx_user_product` (`user_id`, `product_id`) USING BTREE,
    CONSTRAINT `fk_bid_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
    CONSTRAINT `fk_bid_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;