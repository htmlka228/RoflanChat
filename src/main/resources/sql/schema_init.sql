CREATE TABLE IF NOT EXISTS roflan_message (
    uuid uuid NOT NULL,
    roflan_user_uuid uuid NOT NULL,
    message varchar(2048) NOT NULL,
    sent_time TIMESTAMP NOT NULL,
    primary key (uuid)
);

CREATE TABLE IF NOT EXISTS roflan_user (
    uuid uuid NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    enabled boolean NOT NULL,
    primary key (uuid)
);

CREATE TABLE IF NOT EXISTS role (
    id int8 NOT NULL,
    name varchar(255) NOT NULL,
    primary key(id)
);

CREATE TABLE IF NOT EXISTS roflan_user_roles (
    roflan_user_uuid uuid NOT NULL,
    roles_id int8 NOT NULL
);

alter table if exists roflan_message add constraint fk_roflan_message_roflan_user foreign key (roflan_user_uuid) references roflan_user;
alter table if exists roflan_user_roles add constraint fk_roflan_user_roles_roflan_user foreign key (roflan_user_uuid) references roflan_user;
alter table if exists roflan_user_roles add constraint fk_roflan_user_roles_role foreign key (roles_id) references role;
