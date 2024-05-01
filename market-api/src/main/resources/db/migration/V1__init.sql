create table if not exists board
(
    created_at datetime(6) not null,
    id         bigint auto_increment
        primary key,
    like_count bigint      not null,
    updated_at datetime(6) not null,
    writer_id  bigint      not null,
    title      varchar(32) not null,
    content    tinytext    not null
);

create table if not exists category
(
    created_at datetime(6)                   not null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)                   not null,
    name       enum ('A000', 'A001', 'A002') not null
);

create table if not exists comment
(
    board_id   bigint      not null,
    created_at datetime(6) not null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6) not null,
    writer_id  bigint      not null,
    content    tinytext    not null
);

create table if not exists coupon
(
    amount                 int          not null,
    can_use_alone          bit          not null,
    is_discount_percentage bit          not null,
    created_at             datetime(6)  not null,
    id                     bigint auto_increment
        primary key,
    updated_at             datetime(6)  not null,
    name                   varchar(255) not null,
    content                tinytext     not null
);

create table if not exists image
(
    board_id    bigint       null,
    created_at  datetime(6)  not null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)  not null,
    origin_name varchar(255) not null,
    unique_name varchar(255) not null,
    constraint FKil875c0myaxwwf0hty0u1ej2d
        foreign key (board_id) references board (id)
);

create table if not exists like_storage
(
    board_id   bigint      not null,
    created_at datetime(6) not null,
    id         bigint auto_increment
        primary key,
    member_id  bigint      not null,
    updated_at datetime(6) not null
);

create table if not exists member
(
    created_at  datetime(6)              not null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)              not null,
    email       varchar(255)             not null,
    nickname    varchar(255)             not null,
    password    varchar(255)             not null,
    member_role enum ('MEMBER', 'ADMIN') not null,
    constraint UK_mbmcqelty0fbrvxp1q58dn57t
        unique (email)
);

create table if not exists member_coupon
(
    coupon_id  bigint      not null,
    created_at datetime(6) not null,
    id         bigint auto_increment
        primary key,
    member_id  bigint      not null,
    updated_at datetime(6) not null
);

create table if not exists product
(
    contact_count  int                                                                                         not null,
    price          int                                                                                         not null,
    visited_count  int                                                                                         not null,
    category_id    bigint                                                                                      not null,
    created_at     datetime(6)                                                                                 not null,
    id             bigint auto_increment
        primary key,
    member_id      bigint                                                                                      not null,
    updated_at     datetime(6)                                                                                 not null,
    title          varchar(255)                                                                                not null,
    content        tinytext                                                                                    not null,
    location       enum ('BUILDING_THREE', 'BUILDING_FIVE', 'BUILDING_LIBRARY', 'BUILDING_CENTER', 'NEAR_MJU') not null,
    product_status enum ('WAITING', 'RESERVED', 'COMPLETED')                                                   not null
);

create table if not exists schedule_task
(
    execution_time datetime(6)                                  null,
    task_id        varchar(255)                                 not null
        primary key,
    status         enum ('WAITING', 'RUNNING', 'DONE', 'ERROR') null
);

create table if not exists trade_history
(
    product_discount_price int          not null,
    product_origin_price   int          not null,
    buyer_id               bigint       not null,
    created_at             datetime(6)  not null,
    id                     bigint auto_increment
        primary key,
    product_id             bigint       not null,
    seller_id              bigint       not null,
    updated_at             datetime(6)  not null,
    using_coupon_ids       varchar(255) not null
);

create table if not exists voucher
(
    is_public      bit          null,
    is_used        bit          null,
    coupon_id      bigint       null,
    created_at     datetime(6)  not null,
    id             bigint auto_increment
        primary key,
    updated_at     datetime(6)  not null,
    description    varchar(255) null,
    voucher_number varchar(255) null
);
