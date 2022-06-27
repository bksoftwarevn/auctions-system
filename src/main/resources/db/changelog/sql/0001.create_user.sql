--liquibase formatted sql
--changeset tcb:create_user_001 dbms:mysql
--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 select COUNT(*) C from user_tables where UPPER(table_name) = 'USER'
CREATE TABLE "tb_user"  (
    id varchar(36) PRIMARY KEY ,
    username varchar(255) NOT NULL
);