INSERT INTO private_schema.t_car (mark_name, model_name, production_year, price, production_country, colour, count) VALUES
   ('Toyota', 'Corolla', 2022, 20000, 'Japan', 'Blue', 10),
   ('Honda', 'Civic', 2021, 22000, 'Japan', 'Red', 8),
   ('Ford', 'Mustang', 2023, 30000, 'USA', 'Black', 5),
   ('BMW', 'X5', 2022, 50000, 'Germany', 'White', 6),
   ('Audi', 'A4', 2023, 35000, 'Germany', 'Grey', 7);

INSERT INTO private_schema.t_user (firstname, lastname, age, login, password) VALUES
   ('John', 'Doe', 30, 'john_doe', 'password123'),
   ('Jane', 'Smith', 25, 'jane_smith', 'password456'),
   ('Alice', 'Johnson', 35, 'alice_johnson', 'password789'),
   ('Bob', 'Williams', 28, 'bob_williams', 'password321'),
   ('Charlie', 'Brown', 40, 'charlie_brown', 'password654');

INSERT INTO private_schema.t_buy_order (local_date_time, user_id, car_id, approve) VALUES
   ('2024-08-01 10:30:00', 1, 1, TRUE),
   ('2024-08-02 11:00:00', 2, 2, FALSE),
   ('2024-08-03 14:45:00', 3, 3, TRUE),
   ('2024-08-04 09:15:00', 4, 4, FALSE),
   ('2024-08-05 13:30:00', 5, 5, TRUE);

INSERT INTO private_schema.t_service_order (user_id, local_date_time, approve) VALUES
   (1, '2024-08-01 10:30:00', TRUE),
   (2, '2024-08-02 11:00:00', FALSE),
   (3, '2024-08-03 14:45:00', TRUE),
   (4, '2024-08-04 09:15:00', FALSE),
   (5, '2024-08-05 13:30:00', TRUE);
