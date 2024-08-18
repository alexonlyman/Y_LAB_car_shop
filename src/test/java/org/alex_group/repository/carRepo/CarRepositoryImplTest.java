package org.alex_group.repository.carRepo;

import lombok.SneakyThrows;
import org.alex_group.model.cars.Car;
import org.alex_group.utils.ConnectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryImplTest {
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
            }
        }

    }
    @AfterEach
    void clearTable() throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM private_schema.t_car");
            }
        }
    }

    @SneakyThrows
    @Test
    void givenNewCarWhenPutInTableThanReturnNoThrows() {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 10);
        CarRepository carRepository = new CarRepositoryImpl();
        assertDoesNotThrow(() -> carRepository.createCar(car));
    }

    @SneakyThrows
    @Test
    void givenDifferentCarsWhenExecInRepoThenComparisonWithCarsInMap() {
        Car car1 = new Car("Honda", "Accord", 2022, 35000, "Japan", "Black", 8);
        Car car2 = new Car("Ford", "Mustang", 2021, 45000, "USA", "Red", 5);
        Car car3 = new Car("BMW", "3 Series", 2023, 50000, "Germany", "White", 7);
        Car car4 = new Car("Audi", "A4", 2020, 42000, "Germany", "Blue", 6);
        Car car5 = new Car("Chevrolet", "Malibu", 2019, 28000, "USA", "Gray", 12);

        CarRepository carRepository = new CarRepositoryImpl();
        carRepository.createCar(car1);
        carRepository.createCar(car2);
        carRepository.createCar(car3);
        carRepository.createCar(car4);
        carRepository.createCar(car5);

        Map<Integer, Car> expected = new HashMap<>();
        expected.put(car1.getId(), car1);
        expected.put(car2.getId(), car2);
        expected.put(car3.getId(), car3);
        expected.put(car4.getId(), car4);
        expected.put(car5.getId(), car5);

        Map<Integer, Car> result = carRepository.findAllCars();

        assertEquals(expected.size(), result.size(), "The number of cars in the result should be equal to the expected size");

        for (Map.Entry<Integer, Car> entry : expected.entrySet()) {
            assertTrue(result.containsKey(entry.getKey()), "Result should contain car with ID " + entry.getKey());
            assertEquals(entry.getValue(), result.get(entry.getKey()), "Car with ID " + entry.getKey() + " should match");
        }


    }
    @Test
    void givenCarsAndRepoWhenUsingFiltersThenCompareMaps() throws SQLException {
        Car car1 = new Car("Honda", "Accord", 2022, 35000, "Japan", "Black", 8);
        Car car2 = new Car("Ford", "Mustang", 2021, 45000, "USA", "Red", 5);
        CarRepository carRepository = new CarRepositoryImpl();
        carRepository.createCar(car1);
        carRepository.createCar(car2);

        Map<Integer, Car> result = carRepository.findBy("Ford", "Mustang", 50000);

        Map<Integer, Car> expected = new HashMap<>();
        expected.put(car2.getId(), car2);

        assertEquals(expected, result, "The result should match the expected cars for the given filters");
    }


    @Test
    void reservationShouldDecreaseCount() throws SQLException {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 10);
        CarRepository carRepository = new CarRepositoryImpl();
        Car repositoryCar = carRepository.createCar(car);


        Car reservedCar = carRepository.reservation(repositoryCar.getId());

        assertNotNull(reservedCar, "The car should be reserved successfully");
        assertEquals(9, reservedCar.getCount(), "The count of the reserved car should be decreased by 1");

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count FROM private_schema.t_car WHERE id = ?");
            statement.setInt(1, car.getId());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next(), "The car should exist in the database");
            assertEquals(9, resultSet.getInt("count"), "The count in the database should be updated to 9");
        }
    }

    @Test
    void reservationShouldReturnNullIfCarNotFound() {
        CarRepository carRepository = new CarRepositoryImpl();
        Car reservedCar = carRepository.reservation(999); // Assuming 999 is a non-existent ID

        assertNull(reservedCar, "The reservation should return null if the car does not exist");
    }

    @Test
    void reservationShouldReturnNullIfCountIsZero() throws SQLException {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 0);
        CarRepository carRepository = new CarRepositoryImpl();
        carRepository.createCar(car);

        Car reservedCar = carRepository.reservation(car.getId());

        assertNull(reservedCar, "The reservation should return null if the car count is zero");

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT count FROM private_schema.t_car WHERE id = ?");
            statement.setInt(1, car.getId());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next(), "The car should exist in the database");
            assertEquals(0, resultSet.getInt("count"), "The count in the database should remain zero");
        }
    }
    @Test
    void createCarShouldInsertRecord() throws SQLException {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 10);
        CarRepository carRepository = new CarRepositoryImpl();
        carRepository.createCar(car);

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM private_schema.t_car WHERE mark_name = ? AND model_name = ?")) {
            statement.setString(1, car.getMarkName());
            statement.setString(2, car.getModelName());
            try (ResultSet resultSet = statement.executeQuery()) {
                assertTrue(resultSet.next(), "The car should be found in the database");
                assertEquals(car.getMarkName(), resultSet.getString("mark_name"), "Mark name should match");
                assertEquals(car.getModelName(), resultSet.getString("model_name"), "Model name should match");
            }
        }
    }

    @Test
    void givenUpdateCarWhenCompareThenWaitingResultTrue() throws SQLException {
        CarRepository carRepository = new CarRepositoryImpl();

        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 10);
        Car createdCar = carRepository.createCar(car);
        assertNotNull(createdCar, "Car should be created successfully");

        Car updatedCar = new Car("Toyota", "Camry", 2021, 32000, "Japan", "Red", 8);
        boolean isUpdated = carRepository.updateCar(createdCar.getId(), updatedCar);
        assertTrue(isUpdated, "Car should be updated successfully");

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM private_schema.t_car WHERE id = ?");
            statement.setInt(1, createdCar.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Toyota", resultSet.getString("mark_name"), "Mark name should be updated");
                assertEquals("Camry", resultSet.getString("model_name"), "Model name should be updated");
                assertEquals(2021, resultSet.getInt("production_year"), "Production year should be updated");
                assertEquals(32000, resultSet.getDouble("price"), "Price should be updated");
                assertEquals("Japan", resultSet.getString("production_country"), "Production country should be updated");
                assertEquals("Red", resultSet.getString("colour"), "Colour should be updated");
                assertEquals(8, resultSet.getInt("count"), "Count should be updated");
            } else {
                fail("Car with the specified ID should exist in the database");
            }
        }
    }

    @Test
    void givenCarWhenFindingThenTryingToRemove() throws SQLException {
        Car car = new Car("Toyota", "Camry", 2020, 30000, "Japan", "Blue", 10);
        CarRepository carRepository = new CarRepositoryImpl();
        carRepository.createCar(car);
        int carId;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM private_schema.t_car WHERE colour = ?")) {
                statement.setString(1, car.getColour());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    carId = resultSet.getInt("id");
                } else {
                    throw new SQLException("Car not found");
                }
            }
        }
        carRepository.deleteCar(carId);

        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM private_schema.t_car WHERE id = ?")) {
                statement.setInt(1, carId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    assertEquals(0, count, "Car should be deleted from the database");
                }
            }
        }
    }
}