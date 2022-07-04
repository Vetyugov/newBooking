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
        name            varchar(255) NOT NULL,
        patronymic      varchar(255),
        surname         varchar(255) NOT NULL,
        email           varchar(255) UNIQUE,
        username        varchar(255) NOT NULL UNIQUE,
        password        varchar(255) NOT NULL,
        created_at      timestamp default current_timestamp,
        updated_at      timestamp default current_timestamp
   );

insert into guests (name, patronymic, surname, email, username, password)
values ('Наталья', 'Петровна', 'Платонова', 'platonova@gmail.com', 'platonova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i'), --100
       ('Юлия', 'Сергеевна', 'Петрова', 'petrova@gmail.com', 'petrova', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i');
