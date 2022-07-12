create table if not exists granted_authorities
(
    id              bigserial primary key,
    user_user_id    bigint not null,
    role_role_id    bigint not null,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
--    fk_grants_users_user_id FOREIGN KEY (user_user_id)
--        REFERENCES users (id) MATCH SIMPLE,
--    fk_grants_roles_user_id FOREIGN KEY (role_role_id)
--        REFERENCES roles (id) MATCH SIMPLE
);
