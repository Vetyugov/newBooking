create table if not exists roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_GUEST'), --пользователь, арендатор
       ('ROLE_OWNER'), --владелец сервиса, ему нужна статистика, отчеты, прибыль
       ('ROLE_LEGAL_HOST'), --арендодатель, владелец жилья, объекта (юр лицо)
       ('ROLE_INDIVIDUAL_HOST'), --арендодатель, владелец жилья, объекта (физ лицо)
       ('ROLE_ADMIN'); --администратор сайта, поддержка

create table if not exists users
(
    id         bigserial primary key,
    role_id    bigint not null references roles (id),
    username   varchar(36) not null unique,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into users (username, role_id, password, email)
values ('bob', 1, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'), --100 password
       ('john', 2, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com'),
       ('platonova', 1, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'platonova@gmail.com'),
       ('petrova', 1, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'petrova@gmail.com'),
       ('pushkin', 3, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'pushkin@gmail.com'),
       ('gogol', 4, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'gogol@gmail.com'),
       ('admin', 5, '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');


--create table users_roles
--(
--    user_id    bigint not null references users (id),
--    role_id    bigint not null references roles (id),
--    created_at timestamp default current_timestamp,
--    updated_at timestamp default current_timestamp,
--    primary key (user_id, role_id)
--);
--
--insert into users_roles (user_id, role_id)
--values (1, 1),
--       (2, 2),
--       (3, 1),
--       (4, 1),
--       (5, 3),
--       (6, 4),
--       (7, 5);

create table if not exists guests
 (
        id              bigserial primary key,
        user_id         bigint not null references users (id),
        name            varchar(255),
        patronymic      varchar(255),
        surname         varchar(255),
        username        varchar(255) NOT NULL UNIQUE,  -- необязательные
        created_at      timestamp default current_timestamp,
        updated_at      timestamp default current_timestamp
   );

insert into guests (user_id, name, patronymic, surname,  username)
values (1, 'Bob', 'bobovich', 'Bobov', 'bob'),
        (3, 'Наталья', 'Петровна', 'Платонова', 'platonova'), --100
       (4, 'Юлия', 'Сергеевна', 'Петрова', 'petrova');


create table if not exists hosts
(
        id              bigserial primary key,
        user_id         bigint not null references users (id),
        name            varchar(255),
        patronymic      varchar(255),
        surname         varchar(255),
        username        varchar(255) not null UNIQUE,   --
        title_firm      varchar(255),
        country         varchar(255),
        address         varchar(255),
        office_address  varchar(255),
        postcode        varchar(255),
        inn             bigint UNIQUE,
        account         varchar(34),
        created_at      timestamp default current_timestamp,
        updated_at      timestamp default current_timestamp
);

insert into hosts (user_id, name, patronymic, surname, username, title_firm, country, address, office_address, postcode, inn, account)
values (5, 'Александр', 'Сергеевич', 'Пушкин', 'pushkin', 'Pushkin', 'Russia', null, 'Москва, Нахимовский проспект д 61, кв 30', '119335', 1234567891, '5512345678543412'),
       (6, 'Николай', 'Васильевич', 'Гоголь', 'gogol', null, 'Russia', 'Санкт-Петербург, Крыленко д 1, кв 1', null, '192232', 0123456789, '12344321567887');
