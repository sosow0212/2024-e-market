create table if not exists chat
(
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    id         bigint auto_increment primary key,
    chat_room_id  bigint not null,
    sender_id  bigint not null,
    message    tinytext not null
    );

create table if not exists chatting_room
(
    created_at datetime(6) not null,
    updated_at datetime(6) not null,
    id         bigint auto_increment primary key,
    product_id  bigint not null,
    buyer_id  bigint not null,
    seller_id  bigint not null,
    chatting_status enum('PROCESS', 'DONE') not null
    );
