create table if not exists chat
(
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    id         bigint auto_increment
        primary key,
    chat_room_id  bigint      not null,
    message    tinytext    not null
);

