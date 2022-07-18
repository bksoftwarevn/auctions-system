-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_brand
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'brand';

CREATE TABLE `auctions_system`.`brand`
(
    `id`         varchar(36)  NOT NULL,
    `name`       varchar(500) NOT NULL,
    `info`       tinytext     NULL,
    `additional` tinytext     NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idx_name` (`name`) USING HASH
);