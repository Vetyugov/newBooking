drop table if exists apartment_categories cascade;
create table apartment_categories
(
    id          bigserial primary key,
    title       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

drop table if exists apartments cascade;
create table apartments
(
    id                      bigserial primary key,
    title                   varchar(255),
    apartment_category_id   bigint not null references apartment_categories (id),
    city                    varchar(255),
    street                  varchar(255),
    building_number         int not null,
    square_meters           int not null,
    number_of_guests        int not null,
    number_of_rooms         int not null,
    number_of_beds          int not null,
    price_per_night         numeric (8, 2) not null,
    user_name                varchar(255) not null,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
);

drop table if exists booking_dates cascade;
create table booking_dates
(
    id             bigserial primary key,
    booking_start_date   date,
    booking_finish_date  date,
    apartment_id   bigint not null references apartments (id),
    created_at     timestamp default current_timestamp,
    updated_at     timestamp default current_timestamp
);

insert into apartment_categories (title)
values ('Квартира'),
       ('Апартаменты');

insert into apartments (title, apartment_category_id, city, street, building_number, square_meters, number_of_guests, number_of_rooms, number_of_beds, price_per_night, user_name)
values ('Самая лучшая квартира', 1, 'Москва', 'Симоновский вал', 5, 45, 3, 2, 3, 3500.00, 'bob'),
       ('Апартаменты с видом на реку', 2, 'Санкт-Петербург', 'Петроградская наб.', 34, 75, 5, 3, 5, 10500.00, 'tom');

insert into booking_dates (booking_start_date, booking_finish_date,  apartment_id)
values ('2022-07-04', '2022-07-09', 1),
       ('2022-08-01', '2022-08-03', 1),
       ('2022-06-02', '2022-06-15', 1),
       ('2022-05-13', '2022-05-14', 2);







