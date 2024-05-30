create table product_image
(
    created_at  datetime(6)  not null,
    id          bigint auto_increment
        primary key,
    product_id  bigint       null,
    updated_at  datetime(6)  not null,
    origin_name varchar(255) not null,
    unique_name varchar(255) not null,
    constraint FK6oo0cvcdtb6qmwsga468uuukk
        foreign key (product_id) references product (id)
);

