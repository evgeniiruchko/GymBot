CREATE TABLE roles (
                        id BIGINT  UNIQUE NOT NULL,
                        name VARCHAR(20) NOT NULL,
                        PRIMARY KEY (id)
);

INSERT INTO roles (id, name)
VALUES
       (1, 'user'),
       (2, 'administrator');

CREATE TABLE clients (
                         phone_number VARCHAR(15) UNIQUE NOT NULL,
                         first_name VARCHAR(20),
                         middle_name VARCHAR(20),
                         last_name VARCHAR(20),
                         birthday DATE,
                         sex VARCHAR(10),
                         card_number BIGINT,
                         PRIMARY KEY (phone_number)
);

CREATE TABLE users (
                       id BIGINT UNIQUE NOT NULL,
                       is_bot boolean,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       username VARCHAR(50),
                       phone_number VARCHAR(15) UNIQUE,
                       role_id INTEGER DEFAULT 1,

                       PRIMARY KEY (id),
                       CONSTRAINT fk_user_client
                        FOREIGN KEY(phone_number) REFERENCES clients(phone_number),
                        FOREIGN KEY(role_id) REFERENCES roles(id)
);

create unique index user_id_uindex
    on "users" (id);

create unique index client_id_uindex
    on "clients" (phone_number);