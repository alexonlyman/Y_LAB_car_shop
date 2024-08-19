package org.alex_group.repository.impl;

import org.alex_group.model.cars.Car;
import org.alex_group.repository.CarRepository;
import org.alex_group.utils.ConnectionUtil;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * The CarRepositoryImpl class provides implementation for the CarRepository interface.
 * It handles CRUD operations for car entities in the repository using JDBC.
 *
 * This class provides methods to create, read, update, and delete car records in the database.
 */

public class CarRepositoryImpl implements CarRepository {
    private static final String INSERT_CAR_SQL = "INSERT INTO private_schema.t_car (mark_name, model_name, production_year, price, production_country, colour, count) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String SELECT_CAR_BY_ID_SQL = "SELECT * FROM private_schema.t_car WHERE id = ?";
    private static final String SELECT_ALL_CARS_SQL = "SELECT * FROM private_schema.t_car";
    private static final String UPDATE_CAR_SQL = "UPDATE private_schema.t_car SET";
    private static final String DELETE_CAR_SQL = "DELETE FROM private_schema.t_car WHERE id = ?";

    /**
     * Adds a new car to the repository.
     *
     * @param car The car to be added. Must not be {@code null}.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public Car createCar(Car car) throws SQLException {
        try(Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAR_SQL,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, car.getMarkName());
            preparedStatement.setString(2, car.getModelName());
            preparedStatement.setInt(3, car.getProductionYear());
            preparedStatement.setDouble(4, car.getPrice());
            preparedStatement.setString(5, car.getProductionCountry());
            preparedStatement.setString(6, car.getColour());
            preparedStatement.setInt(7, car.getCount());

            int affectedRows = preparedStatement.executeUpdate();
            int id = 0;
            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    car.setId(id);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return car;
    }

    /**
     * Retrieves all cars in the repository.
     *
     * @return A map of all cars, with car IDs as keys and car objects as values.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public Map <Integer,Car > findAllCars() {
        Map<Integer, Car> result = new HashMap<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARS_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("mark_name"),
                        resultSet.getString("model_name"),
                        resultSet.getInt("production_year"),
                        resultSet.getInt("price"),
                        resultSet.getString("production_country"),
                        resultSet.getString("colour"),
                        resultSet.getInt("count")
                );
              car.setId(resultSet.getInt("id"));
                result.put(car.getId(), car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Finds cars in the repository by brand, model, and maximum price.
     *
     * @param brand    The brand of the car (can be {@code null}).
     * @param model    The model of the car (can be {@code null}).
     * @param maxPrice The maximum price of the car (can be {@code null}).
     * @return A map of cars that match the criteria, with car IDs as keys and car objects as values.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public Map<Integer,Car> findBy(String brand, String model, Integer maxPrice) {
        Map<Integer, Car> result = new HashMap<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM private_schema.t_car WHERE 1=1");

        if (brand != null) {
            sqlBuilder.append(" AND mark_name = ?");
        }
        if (model != null) {
            sqlBuilder.append(" AND model_name = ?");
        }
        if (maxPrice != null) {
            sqlBuilder.append(" AND price <= ?");
        }

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            int index = 1;
            if (brand != null) {
                preparedStatement.setString(index++, brand);
            }
            if (model != null) {
                preparedStatement.setString(index++, model);
            }
            if (maxPrice != null) {
                preparedStatement.setInt(index++, maxPrice);
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("mark_name"),
                        resultSet.getString("model_name"),
                        resultSet.getInt("production_year"),
                        resultSet.getInt("price"),
                        resultSet.getString("production_country"),
                        resultSet.getString("colour"),
                        resultSet.getInt("count")
                );
                car.setId(resultSet.getInt("id"));
                result.put(car.getId(), car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

     /**
     * Reserves a car by its ID, reducing its count by 1 if available.
     *
     * @param id The ID of the car to be reserved.
     * @return The reserved car with updated count, or {@code null} if not available or not found.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public Car reservation(Integer id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement selectStatement = connection.prepareStatement(SELECT_CAR_BY_ID_SQL);
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    // Update count
                    PreparedStatement updateStatement = connection.prepareStatement("UPDATE private_schema.t_car SET count = count - 1 WHERE id = ?");
                    updateStatement.setInt(1, id);
                    updateStatement.executeUpdate();

                    // Return car with updated count
                    return new Car(
                            resultSet.getString("mark_name"),
                            resultSet.getString("model_name"),
                            resultSet.getInt("production_year"),
                            resultSet.getInt("price"),
                            resultSet.getString("production_country"),
                            resultSet.getString("colour"),
                            count - 1
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Updates the details of a car in the repository.
     *
     * @param id         The ID of the car to be updated.
     * @param updatedCar The car object containing updated details. Fields with {@code null} values will not be updated.
     * @return {@code true} if the car was successfully updated, {@code false} otherwise.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public boolean updateCar(Integer id, Car updatedCar) {
        StringBuilder sqlBuilder = getStringBuilder(updatedCar);

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            int index = 1;
            if (updatedCar.getMarkName() != null) {
                preparedStatement.setString(index++, updatedCar.getMarkName());
            }
            if (updatedCar.getModelName() != null) {
                preparedStatement.setString(index++, updatedCar.getModelName());
            }
            if (updatedCar.getProductionYear() != null) {
                preparedStatement.setInt(index++, updatedCar.getProductionYear());
            }
            if (updatedCar.getPrice() != null) {
                preparedStatement.setDouble(index++, updatedCar.getPrice());
            }
            if (updatedCar.getProductionCountry() != null) {
                preparedStatement.setString(index++, updatedCar.getProductionCountry());
            }
            if (updatedCar.getColour() != null) {
                preparedStatement.setString(index++, updatedCar.getColour());
            }
            if (updatedCar.getCount() != null) {
                preparedStatement.setInt(index++, updatedCar.getCount());
            }
            preparedStatement.setInt(index, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull StringBuilder getStringBuilder(Car updatedCar) {
        StringBuilder sqlBuilder = new StringBuilder(UPDATE_CAR_SQL);

        boolean hasFieldsToUpdate = false;

        if (updatedCar.getMarkName() != null) {
            sqlBuilder.append(" mark_name = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getModelName() != null) {
            sqlBuilder.append(" model_name = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getProductionYear() != null) {
            sqlBuilder.append(" production_year = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getPrice() != null) {
            sqlBuilder.append(" price = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getProductionCountry() != null) {
            sqlBuilder.append(" production_country = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getColour() != null) {
            sqlBuilder.append(" colour = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedCar.getCount() != null) {
            sqlBuilder.append(" count = ?,");
            hasFieldsToUpdate = true;
        }

        if (!hasFieldsToUpdate) {
            throw new IllegalArgumentException("No fields to update");
        }

        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE id = ?");

        return sqlBuilder;
    }

    /**
     * Deletes a car from the repository by its ID.
     *
     * @param id The ID of the car to be deleted.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public void deleteCar(Integer id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CAR_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
