create schema if not exists private_schema;

CREATE TABLE IF NOT EXISTS private_schema.t_car (
    id SERIAL PRIMARY KEY,
    mark_name VARCHAR(255) NOT NULL,
    model_name VARCHAR(255) NOT NULL,
    production_year INTEGER NOT NULL,
    price INTEGER NOT NULL,
    production_country VARCHAR(255) NOT NULL,
    colour VARCHAR(255) NOT NULL,
    count INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS private_schema.t_user (
    id SERIAL PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL ,
    lastname VARCHAR(255) NOT NULL ,
    age INT,
    login VARCHAR(255) NOT NULL ,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS private_schema.t_buy_order (
    id SERIAL PRIMARY KEY,
    local_date_time TIMESTAMP NOT NULL,
    user_id INTEGER NOT NULL,
    car_id INTEGER NOT NULL,
    approve BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES private_schema.t_user(id),
    FOREIGN KEY (car_id) REFERENCES private_schema.t_car(id)
);
CREATE TABLE IF NOT EXISTS private_schema.t_service_order(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    local_date_time TIMESTAMP NOT NULL,
    approve BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES private_schema.t_user(id)

);