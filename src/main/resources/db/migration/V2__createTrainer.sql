CREATE TABLE trainers (
                         id BIGINT UNIQUE NOT NULL,
                         first_name VARCHAR(20),
                         middle_name VARCHAR(20),
                         last_name VARCHAR(20),
                         photo VARCHAR(200),
                         description TEXT,
                         PRIMARY KEY (id)
);