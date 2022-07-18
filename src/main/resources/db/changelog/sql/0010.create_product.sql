-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_product
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'product';

CREATE TABLE `auctions_system`.`product`
(
    `id`           varchar(36)  NOT NULL,
    `name`         varchar(500) NOT NULL,
    `start_price`  varchar(50)  NOT NULL,
    `series`       varchar(255) NULL,
    `brand_id`     varchar(36)  NOT NULL,
    `buyer`     varchar(36)  NOT NULL,
    `descriptions` tinytext     NOT NULL,
    `main_image`   varchar(500) NULL,
    `images`       tinytext     NULL,
    `auction_id`   varchar(36)  NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_name` (`name`) USING HASH,
    INDEX `idx_brand` (`brand_id`) USING HASH,
    CONSTRAINT `fk_product_brand` FOREIGN KEY (`brand_id`) REFERENCES `auctions_system`.`brand` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_product_auctions` FOREIGN KEY (`auction_id`) REFERENCES `auctions_system`.`auctions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);