-- auto-generated definition
create table user
(
    user_id      bigint auto_increment
        primary key,
    address      varchar(100) null,
    password     varchar(50)  null,
    phone_number varchar(13)  null,
    user_name    varchar(50)  null
);

