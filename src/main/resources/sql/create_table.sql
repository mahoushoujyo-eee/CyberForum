create database if not exists cyber_forum;

use cyber_forum;

create table if not exists user
(
    id bigint primary key auto_increment,
    user_name varchar(20) not null unique,
    encrypted_password varchar(200) not null,
    email varchar(50) not null unique
);

create table if not exists blog
(
    id          bigint primary key auto_increment,
    title       varchar(50)  not null,
    content     varchar(500) not null,
    forum_id    bigint          not null,
    user_id     bigint          not null,
    create_time timestamp    not null
);

create table if not exists comment
(
    id          bigint primary key auto_increment,
    content     varchar(500) not null,
    user_id     bigint       not null,
    blog_id     bigint       not null,
    create_time timestamp    not null
);

create table if not exists forum
(
    id bigint primary key auto_increment,
    name varchar(20) not null unique,
    master_id bigint not null
);

create table if not exists administrator
(
    administrator_id bigint not null,
    forum_id bigint not null
);

alter table blog add is_top boolean default false;
alter table comment add is_top boolean default false;
