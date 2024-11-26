-- we don't know how to generate root <with-no-name> (class Root) :(

grant select on performance_schema.* to 'mysql.session'@localhost;

grant trigger on sys.* to 'mysql.sys'@localhost;

grant audit_abort_exempt, firewall_exempt, select, system_user on *.* to 'mysql.infoschema'@localhost;

grant audit_abort_exempt, authentication_policy_admin, backup_admin, clone_admin, connection_admin, firewall_exempt, persist_ro_variables_admin, session_variables_admin, shutdown, super, system_user, system_variables_admin on *.* to 'mysql.session'@localhost;

grant audit_abort_exempt, firewall_exempt, system_user on *.* to 'mysql.sys'@localhost;

grant allow_nonexistent_definer, alter, alter routine, application_password_admin, audit_abort_exempt, audit_admin, authentication_policy_admin, backup_admin, binlog_admin, binlog_encryption_admin, clone_admin, connection_admin, create, create role, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, drop role, encryption_key_admin, event, execute, file, firewall_exempt, flush_optimizer_costs, flush_status, flush_tables, flush_user_resources, group_replication_admin, group_replication_stream, index, innodb_redo_log_archive, innodb_redo_log_enable, insert, lock tables, passwordless_user_admin, persist_ro_variables_admin, process, references, reload, replication client, replication slave, replication_applier, replication_slave_admin, resource_group_admin, resource_group_user, role_admin, select, sensitive_variables_observer, service_connection_admin, session_variables_admin, set_any_definer, show databases, show view, show_routine, shutdown, super, system_user, system_variables_admin, table_encryption_admin, telemetry_log_admin, trigger, update, xa_recover_admin, grant option on *.* to root@localhost;

create table administrator
(
    administrator_id bigint not null,
    forum_id         bigint not null
);

create table blog
(
    id          bigint auto_increment
        primary key,
    title       varchar(50)          not null,
    content     varchar(500)         not null,
    user_id     bigint               not null,
    forum_id    bigint               not null,
    create_time timestamp            not null,
    is_top      tinyint(1) default 0 null
);

create table comment
(
    id          bigint auto_increment
        primary key,
    content     varchar(500)         not null,
    user_id     bigint               not null,
    blog_id     bigint               not null,
    create_time timestamp            not null,
    is_top      tinyint(1) default 0 null
);

create table forum
(
    id       bigint auto_increment
        primary key,
    name     varchar(20) not null,
    owner_id bigint      not null,
    constraint name
        unique (name)
);

create table user
(
    id                 bigint auto_increment
        primary key,
    user_name          varchar(20)  not null,
    encrypted_password varchar(200) not null,
    email              varchar(50)  not null,
    constraint email
        unique (email),
    constraint user_name
        unique (user_name)
);



