create table if not exists product_like
(
    product_id   bigint      not null,
    created_at datetime(6) not null,
    id         bigint auto_increment
    primary key,
    member_id  bigint      not null,
    updated_at datetime(6) not null
    );
