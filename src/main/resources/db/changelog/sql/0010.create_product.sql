-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_product
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'product';

CREATE TABLE `product`
(
    `id`           varchar(36) COLLATE utf8_unicode_ci  NOT NULL,
    `name`         varchar(500) COLLATE utf8_unicode_ci NOT NULL,
    `start_price`  decimal(30, 0)                       NOT NULL,
    `brand_id`     varchar(36) COLLATE utf8_unicode_ci  NOT NULL,
    `descriptions` tinytext COLLATE utf8_unicode_ci     NOT NULL,
    `main_image`   varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
    `images`       tinytext COLLATE utf8_unicode_ci     DEFAULT NULL,
    `auction_id`   varchar(36) COLLATE utf8_unicode_ci  NOT NULL,
    `series`       varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
    `buyer`        varchar(36) COLLATE utf8_unicode_ci  DEFAULT NULL,
    `max_bid`      decimal(30, 0)                       NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_name` (`name`) USING HASH,
    KEY `idx_brand` (`brand_id`) USING HASH,
    KEY `fk_product_auctions` (`auction_id`),
    CONSTRAINT `fk_product_auctions` FOREIGN KEY (`auction_id`) REFERENCES `auctions` (`id`),
    CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;