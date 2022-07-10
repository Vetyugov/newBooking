create table if not exists users
(
    id         bigserial primary key,
    username   varchar(36) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    name       varchar(255),
    patronymic  varchar(255),
    surname     varchar(255),
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

insert into roles (name)
values ('ROLE_GUEST'), --пользователь, арендатор
       ('ROLE_OWNER'), --владелец сервиса, ему нужна статистика, отчеты, прибыль
       ('ROLE_LEGAL_HOST'), --арендодатель, владелец жилья, объекта (юр лицо)
       ('ROLE_INDIVIDUAL_HOST'), --арендодатель, владелец жилья, объекта (физ лицо)
       ('ROLE_ADMIN'); --администратор сайта, поддержка

insert into users (username, password, email, name, patronymic, surname)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com', null, null, null), --100 password
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com', null, null, null),
       ('platonova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'platonova@gmail.com', null, null, null),
       ('petrova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'petrova@gmail.com', null, null, null),
       ('pushkin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'pushkin@gmail.com', null, null, null),
       ('gogol', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'gogol@gmail.com', null, null, null),
       ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com', null, null, null);

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 1),
       (4, 1),
       (5, 3),
       (6, 3),
       (7, 4);

create table hosts
(
        id              bigserial primary key,
        --user_id         bigint not null references users (id),
        name            varchar(255) not null,
        patronymic      varchar(255),
        surname         varchar(255) not null,
        email           varchar(255) UNIQUE,
        username        varchar(255) not null UNIQUE,
        password        varchar(255) not null,
        title_firm      varchar(255),
        country         varchar(255) not null,
        address         varchar(255),
        office_address  varchar(255),
        postcode        bigint not null,
        inn             bigint not null UNIQUE,
        account         bigint not null,
        created_at      timestamp default current_timestamp,
        updated_at      timestamp default current_timestamp
);

insert into hosts (name, patronymic, surname, email, username, password, title_firm, country, address, office_address, postcode, inn, account)
values ('Александр', 'Сергеевич', 'Пушкин', 'aspushkin@gmail.com', 'pushkin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Pushkin', 'Russia', null, 'Москва, Нахимовский проспект д 61, кв 30', 119335, 1234567891, 5512345678543434),
       ('Николай', 'Васильевич', 'Гоголь', 'gogol@gmail.com', 'gogol', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', null, 'Russia', 'Санкт-Петербург, Крыленко д 1, кв 1', null, 192232, 0123456789, 1234432156788765);

create table guests
(
        id              bigserial primary key,
        user_id         bigint not null references users (id),
        name            varchar(255) NOT NULL,
        patronymic      varchar(255),
        surname         varchar(255) NOT NULL,
        email           varchar(255) UNIQUE,
        username        varchar(255) NOT NULL UNIQUE,
        password        varchar(255) NOT NULL,
        created_at      timestamp default current_timestamp,
        updated_at      timestamp default current_timestamp
   );

insert into guests (user_id, name, patronymic, surname, email, username, password)
values (1, 'bob', 'bob', 'bob', 'bob_johnson@gmail.com', 'bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'),
        (3, 'Наталья', 'Петровна', 'Платонова', 'platonova@gmail.com', 'platonova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'), --100
       (4, 'Юлия', 'Сергеевна', 'Петрова', 'petrova@gmail.com', 'petrova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');
