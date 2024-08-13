package org.alex_group.repository.orderRepo;

import org.alex_group.model.cars.Car;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.alex_group.utils.ConnectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepoImplTest {
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeEach
    public void setUp() throws SQLException {
        postgresContainer.start();
        System.setProperty("jdbc:postgresql://localhost:5432/testdb", postgresContainer.getJdbcUrl());
        System.setProperty("postgres", postgresContainer.getUsername());
        System.setProperty("password", postgresContainer.getPassword());

        ConnectionUtil.setConnectionDetails(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("create schema if not exists private_schema;");
                statement.execute("CREATE TABLE if not exists private_schema.t_car (" +
                        "id SERIAL PRIMARY KEY, " +
                        "mark_name VARCHAR(255), " +
                        "model_name VARCHAR(255), " +
                        "production_year INT, " +
                        "price INT, " +
                        "production_country VARCHAR(255), " +
                        "colour VARCHAR(255), " +
                        "count INT)");
                ;
                statement.execute("CREATE TABLE IF NOT EXISTS private_schema.t_user (" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "    firstname VARCHAR(255) NOT NULL ,\n" +
                        "    lastname VARCHAR(255) NOT NULL ,\n" +
                        "    age INT,\n" +
                        "    login VARCHAR(255) NOT NULL ,\n" +
                        "    password VARCHAR(255) NOT NULL)");

                statement.execute("CREATE TABLE IF NOT EXISTS private_schema.t_buy_order (" +
                        "id SERIAL PRIMARY KEY, " +
                        "local_date_time TIMESTAMP NOT NULL, " +
                        "user_id INTEGER NOT NULL, " +
                        "car_id INTEGER NOT NULL, " +
                        "approve BOOLEAN NOT NULL DEFAULT FALSE, " +
                        "FOREIGN KEY (user_id) REFERENCES private_schema.t_user(id), " +
                        "FOREIGN KEY (car_id) REFERENCES private_schema.t_car(id))");
                statement.execute("CREATE TABLE IF NOT EXISTS private_schema.t_service_order (" +
                        "id SERIAL PRIMARY KEY ," +
                        "user_id INTEGER NOT NULL, " +
                        "local_date_time TIMESTAMP NOT NULL, " +
                        "approve BOOLEAN NOT NULL DEFAULT FALSE, " +
                        "FOREIGN KEY (user_id) REFERENCES private_schema.t_user(id))");
            }
        }
    }
    @AfterEach
    void clearTable() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM private_schema.t_service_order");
                statement.execute("DELETE FROM private_schema.t_buy_order");

                statement.execute("DELETE FROM private_schema.t_car");
                statement.execute("DELETE FROM private_schema.t_user");
            }
        }
    }

    @Test
    public void createBuyOrderShouldInsertOrder() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO private_schema.t_user (firstname, lastname, age, login, password) \n" +
                        "VALUES ('test_name', 'test_user', 22, 'testlogin', 'testpassword')");

                statement.execute("INSERT INTO private_schema.t_car (id, mark_name) VALUES (1, 'Honda')");


            }
        }
        User user = new User("test_name", "test_user", 22, "testlogin", "testpassword");
        user.setId(1);
        Car car = new Car("Honda", "Accord", 2022, 35000, "Japan", "Black", 8);
        car.setId(1);
        BuyOrder buyOrder = new BuyOrder(
                1, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), user, car
        );
        buyOrder.setApprove(true);

        OrderRepoImpl orderRepo = new OrderRepoImpl();
        orderRepo.createBuyOrder(buyOrder);

        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM private_schema.t_buy_order WHERE user_id = ? AND car_id = ?")) {
                statement.setInt(1, buyOrder.getUser().getId());
                statement.setInt(2, buyOrder.getCar().getId());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    assertEquals(buyOrder.getLocalDateTime().truncatedTo(ChronoUnit.SECONDS), resultSet.getTimestamp("local_date_time").toLocalDateTime());
                    assertEquals(buyOrder.getUser().getId(), resultSet.getInt("user_id"));
                    assertEquals(buyOrder.getCar().getId(), resultSet.getInt("car_id"));
                    assertEquals(buyOrder.isApprove(), resultSet.getBoolean("approve"));
                } else {
                    throw new AssertionError("Buy order was not found in the database");
                }
            }
        }
    }
    @Test
    public void createServiceOrderShouldInsertOrder() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                statement.execute("INSERT INTO private_schema.t_user (firstname, lastname, age, login, password) \n" +
                        "VALUES ('John', 'Doe', 30, 'johndoe', 'password123')");
            }
        }


        User user = new User("test_name", "test_user", 22, "testlogin", "testpassword");
        user.setId(1);


        ServiceOrder serviceOrder = new ServiceOrder(
               1, user, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );
        serviceOrder.setApprove(true);

        OrderRepo orderRepo = new OrderRepoImpl();
        orderRepo.createServiceOrder(serviceOrder);

        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM private_schema.t_service_order WHERE user_id = ?")) {
                statement.setInt(1, serviceOrder.getUser().getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        assertEquals(serviceOrder.getUser().getId(), resultSet.getInt("user_id"));
                        assertEquals(serviceOrder.getLocalDateTime().truncatedTo(ChronoUnit.SECONDS),
                                resultSet.getTimestamp("local_date_time").toLocalDateTime());
                        assertEquals(serviceOrder.isApprove(), resultSet.getBoolean("approve"));
                    } else {
                        throw new AssertionError("Service order was not found in the database");
                    }
                }
            }
        }
    }

    @Test
    public void findAllBuyOrdersShouldReturnAllOrders() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO private_schema.t_user (firstname, lastname, age, login, password) " +
                        "VALUES ('John', 'Doe', 30, 'johndoe', 'password123')");

                statement.execute("INSERT INTO private_schema.t_car (" +
                        "    mark_name, model_name, production_year, price, production_country, colour, count" +
                        ") VALUES (" +
                        "    'Toyota', 'Camry', 2022, 30000, 'Japan', 'Black', 10" +
                        ")");

                statement.execute("INSERT INTO private_schema.t_buy_order (local_date_time, user_id, car_id, approve) " +
                        "VALUES ('2024-08-12 12:00:00', 1, 1, TRUE)");

                statement.execute("INSERT INTO private_schema.t_buy_order (local_date_time, user_id, car_id, approve) " +
                        "VALUES ('2024-08-13 13:00:00', 1, 1, FALSE)");
            }
        }

        OrderRepo orderRepo = new OrderRepoImpl();

        List<BuyOrder> buyOrders = orderRepo.findAllBuyOrders();

        assertEquals(2, buyOrders.size(), "The number of buy orders should be 2");

        BuyOrder firstOrder = buyOrders.get(0);
        assertEquals(1, firstOrder.getUser().getId(), "User ID should be 1");
        assertEquals(1, firstOrder.getCar().getId(), "Car ID should be 1");
        assertEquals(LocalDateTime.of(2024, 8, 12, 12, 0), firstOrder.getLocalDateTime(), "Local date time should match");
        assertTrue(firstOrder.isApprove(), "Order should be approved");

        BuyOrder secondOrder = buyOrders.get(1);
        assertEquals(1, secondOrder.getUser().getId(), "User ID should be 1");
        assertEquals(1, secondOrder.getCar().getId(), "Car ID should be 1");
        assertEquals(LocalDateTime.of(2024, 8, 13, 13, 0), secondOrder.getLocalDateTime(), "Local date time should match");
        assertFalse(secondOrder.isApprove(), "Order should not be approved");
    }

    @Test
    public void findAllServiceOrdersShouldReturnAllOrders() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO private_schema.t_user (id, firstname, lastname, age, login, password) " +
                    "VALUES (1, 'test_name', 'test-user', 2, 'testlogin', 'testpassword')");

            statement.execute("INSERT INTO private_schema.t_service_order (user_id, local_date_time, approve) " +
                    "VALUES (1, '2024-08-12 12:00:00', TRUE)");

            statement.execute("INSERT INTO private_schema.t_service_order (user_id, local_date_time, approve) " +
                    "VALUES (1, '2024-08-13 13:00:00', FALSE)");
        }

        OrderRepo orderRepo = new OrderRepoImpl();

        List<ServiceOrder> serviceOrders = orderRepo.findAllServiceOrders();

        assertEquals(2, serviceOrders.size(), "The number of service orders should be 2");

        ServiceOrder firstOrder = serviceOrders.get(0);
        assertEquals(1, firstOrder.getUser().getId(), "User ID should be 1");
        assertEquals(LocalDateTime.of(2024, 8, 12, 12, 0), firstOrder.getLocalDateTime(), "Local date time should match");
        assertTrue(firstOrder.isApprove(), "Order should be approved");

        ServiceOrder secondOrder = serviceOrders.get(1);
        assertEquals(1, secondOrder.getUser().getId(), "User ID should be 1");
        assertEquals(LocalDateTime.of(2024, 8, 13, 13, 0), secondOrder.getLocalDateTime(), "Local date time should match");
        assertFalse(secondOrder.isApprove(), "Order should not be approved");
    }

    @Test
    public void updateCarServiceRequestShouldUpdateStatus() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO private_schema.t_user (id, firstname, lastname, age, login, password) " +
                    "VALUES (1, 'John', 'Doe', 30, 'johndoe', 'password123')");

            statement.execute("INSERT INTO private_schema.t_service_order (id, user_id, local_date_time, approve) " +
                    "VALUES (1, 1, '2024-08-12 12:00:00', FALSE)");
        }

        OrderRepo orderRepo = new OrderRepoImpl();

        boolean isUpdated = orderRepo.updateCarServiceRequest(1);

        assertTrue(isUpdated, "Service order should be updated");

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT approve FROM private_schema.t_service_order WHERE id = ?")) {
            preparedStatement.setInt(1, 1);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    assertTrue(resultSet.getBoolean("approve"), "Service order should be approved");
                } else {
                    throw new AssertionError("Service order with ID 1 was not found in the database");
                }
            }
        }
    }



}