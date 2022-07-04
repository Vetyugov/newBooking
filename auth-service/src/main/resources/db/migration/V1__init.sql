create table users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

--create table users if not exists
--(
--    id          bigserial not null primary key,
--    name        varchar(255),
--    patronymic  varchar(255),
--    surname     varchar(255),
--    username    varchar(255) not null unique,
--    password    varchar(255) not null,
--    email       varchar(255) unique,
--    country     varchar(255),
--    title_firm  varchar(255),
--    address     varchar(255),
--    office_address varchar(255),
--    postcode    bigint,
--    inn         bigint,
--    account     bigint,
--    created_at  timestamp default current_timestamp,
--    updated_at  timestamp default current_timestamp
--);

create table if not exists roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table users_roles
(
    user_id    bigint not null references users (id),
    role_id    bigint not null references roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);

--insert into roles (name)
--values ('ROLE_USER'),
--       ('ROLE_ADMIN');

insert into roles (name)
values ('ROLE_GUEST'), --пользователь, арендатор
       ('ROLE_OWNER'), --владелец сервиса, ему нужна статистика, отчеты, прибыль
       ('ROLE_LEGAL_HOST'), --арендодатель, владелец жилья, объекта (юр лицо)
       ('ROLE_INDIVIDUAL_HOST'), --арендодатель, владелец жилья, объекта (физ лицо)
       ('ROLE_ADMIN'); --администратор сайта, поддержка

insert into users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'), --100 password
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com'),
       ('platonova','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'platonova@gmail.com'),
       ('petrova','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'petrova@gmail.com'),
       ('pushkin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'pushkin@gmail.com'),
       ('gogol', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'gogol@gmail.com'),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 4),
       (2, 2),
       (3, 1),
       (4, 1),
       (5, 3),
       (6, 3),
       (7, 4);