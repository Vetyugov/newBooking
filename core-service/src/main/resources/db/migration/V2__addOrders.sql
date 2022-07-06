
create table order_status (
    id                  bigserial primary key,
    description         varchar(255)  not null,
    descriptionRU       varchar(255)  not null
);

insert into order_status (description, descriptionRU)
values ('selected', 'выбран'),
       ('unselected', 'не выбран'),
       ('awaiting payment', 'ожидает оплаты'),
       ('booked', 'забронирован'),
       ('canceled', 'отменён'),
       ('completed', 'выполнен');

create table orders
(
    id                  bigserial primary key,
    user_name           varchar(255)  not null,
    apartment_id        bigserial  not null,
    apartment_check_in  timestamp not null,
    apartment_check_out timestamp not null,
    price               numeric(8, 2) not null,
    total_price         numeric(8, 2) not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp,
    status_id            bigserial not null references order_status (id)
);



insert into orders (user_name, apartment_id, apartment_check_in, apartment_check_out, price, total_price, status_id)
values ('bob', 1, '2022-01-26', '2022-01-30', 2000.50, 2000.50, 1),
       ('mike', 2, '2022-02-23', '2022-02-26', 1600.00, 1600.00, 4);

