-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_users
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'users';

CREATE TABLE `auctions_system`.`users`
(
    `id`           varchar(36)  NOT NULL,
    `username`     varchar(255) NOT NULL,
    `password`     varchar(500) NOT NULL,
    `email`        varchar(255) NOT NULL,
    `name`         varchar(100) NOT NULL,
    `phone`        varchar(16)  NOT NULL,
    `avatar`       varchar(255) NULL,
    `address`      varchar(255) NULL,
    `lang`         varchar(255) NOT NULL DEFAULT 'vi',
    `active_key`   varchar(255) NULL,
    `active`       boolean               DEFAULT 0,
    `created_date` datetime(0)  NOT NULL,
    `updated_date` datetime(0)  NULL,
    `lock`         boolean               DEFAULT 0,
    `additional`   tinytext     NULL,
    `citizen_id`   varchar(16)  NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `idx_username` (`username`) USING HASH,
    UNIQUE INDEX `idx_email` (`email`) USING HASH,
    INDEX `idx_phone` (`phone`) USING HASH
);

INSERT INTO `users` (`id`, `username`, `password`, `email`, `name`, `phone`, `avatar`, `address`, `lang`, `active_key`, `active`, `created_date`, `updated_date`, `lock`, `additional`, `citizen_id`) VALUES ('33173940-7c1c-4b99-9c5c-e271469ec4cb', 'admin', '$2a$10$QSJ7Nr8c84Ilj4XnVQSMw.ZGCyuSs5WTrTz5xqQO92ku6ctiGgXqq', 'admin@bksoftwarevn.com', 'Admin', '0906505816', 'admin-avatar.com', 'Hà Nội', 'vi', NULL, 1, '2022-07-16 02:49:45', '2022-07-16 02:51:20', 0, NULL, '125504503');
