-- liquibase formatted sql
-- changeset liquibase:1 myname:create_table_role
-- preconditions onFail:MARK_RAN onError:HALT
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'user_role';

CREATE TABLE `auctions_system`.`user_role`
(
    `user_id` varchar(36) NOT NULL,
    `role_id` int(5)      NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_users_id` FOREIGN KEY (`user_id`) REFERENCES `auctions_system`.`users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `auctions_system`.`role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES ('33173940-7c1c-4b99-9c5c-e271469ec4cb', 1);
