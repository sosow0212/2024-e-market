create table market.mail_storage
(
    created_at  datetime(6)                   not null,
    id          bigint auto_increment
        primary key,
    member_id   bigint                        null,
    updated_at  datetime(6)                   not null,
    email       varchar(255)                  null,
    nickname    varchar(255)                  null,
    mail_status enum ('WAIT', 'FAIL', 'DONE') null
);
