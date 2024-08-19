package org.alex_group.repository.impl;

import org.alex_group.logging.AuditLog;
import org.alex_group.model.cars.Car;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.alex_group.repository.OrderRepo;
import org.alex_group.service.impl.AuthServiceImpl;
import org.alex_group.utils.ConnectionUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Implementation of the OrderRepo interface that manages a collection of buy orders and service orders.
 */
public class OrderRepoImpl implements OrderRepo {
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to be found.
     * @return The user with the specified ID, or {@code null} if no user is found.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    public User findUserById(Integer id) throws SQLException {
        String findUserById = "SELECT * FROM private_schema.t_user WHERE ID = ?";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findUserById);
            statement.setInt(1, id);
            statement.execute();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getInt("age"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                    );
                    user.setId(resultSet.getInt("id"));
                }
            }
        }
        return user;

    }

    /**
     * Finds a car by its ID.
     *
     * @param id The ID of the car to be found.
     * @return The car with the specified ID, or {@code null} if no car is found.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    public Car findCarById(Integer id) throws SQLException {
        String findCarByIdSql = "SELECT * FROM private_schema.t_car WHERE id = ?";
        Car car = null;

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findCarByIdSql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    car = new Car(
                            resultSet.getString("mark_name"),
                            resultSet.getString("model_name"),
                            resultSet.getInt("production_year"),
                            resultSet.getInt("price"),
                            resultSet.getString("production_country"),
                            resultSet.getString("colour"),
                            resultSet.getInt("count")
                    );
                    car.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            logger.severe("Error finding car by ID: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return car;

    }


    /**
     * Creates a new buy order and adds it to the repository.
     *
     * @param buyOrder The buy order to be added. Must not be {@code null}.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public void createBuyOrder(BuyOrder buyOrder) {
        String insertBuyOrderSql = "INSERT INTO private_schema.t_buy_order (local_date_time, user_id, car_id,approve) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertBuyOrderSql)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(buyOrder.getLocalDateTime()));
            preparedStatement.setInt(2, buyOrder.getUser().getId());
            preparedStatement.setInt(3, buyOrder.getCar().getId());
            preparedStatement.setBoolean(4, buyOrder.isApprove());

            preparedStatement.executeUpdate();
            AuditLog.log("buy-order was created: " + buyOrder);
        } catch (SQLException e) {
            logger.severe("Error creating buy order: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new service order and adds it to the repository.
     *
     * @param serviceOrder The service order to be added. Must not be {@code null}.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public void createServiceOrder(ServiceOrder serviceOrder) {
        String insertServiceOrderSql = "INSERT INTO private_schema.t_service_order (user_id,local_date_time,approve) VALUES (?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertServiceOrderSql)) {
            preparedStatement.setInt(1, serviceOrder.getUser().getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(serviceOrder.getLocalDateTime()));
            preparedStatement.setBoolean(3, serviceOrder.isApprove());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Error creating service order: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves all buy orders in the repository.
     *
     * @return A list of all buy orders.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public List<BuyOrder> findAllBuyOrders() throws SQLException {
        String selectAllOrders = "SELECT * FROM private_schema.t_buy_order";
        List<BuyOrder> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllOrders);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                LocalDateTime dateTime = resultSet.getTimestamp("local_date_time").toLocalDateTime();
                User user = findUserById(resultSet.getInt("user_id"));
                Car car = findCarById(resultSet.getInt("car_id"));
                boolean approve = resultSet.getBoolean("approve");
                BuyOrder buyOrder = new BuyOrder(
                        dateTime,
                        user,
                        car
                );
                buyOrder.setId(resultSet.getInt("id"));
                buyOrder.setApprove(approve);
                result.add(buyOrder);
            }

        }
        return result;
    }

    /**
     * Retrieves all service orders in the repository.
     *
     * @return A list of all service orders.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public List<ServiceOrder> findAllServiceOrders() throws SQLException {
        String selectAllOrders = "SELECT * FROM private_schema.t_service_order";
        List<ServiceOrder> result = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllOrders);
             ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            while (resultSet.next()) {
                User user = findUserById(resultSet.getInt("user_id"));
                LocalDateTime dateTime = resultSet.getTimestamp("local_date_time").toLocalDateTime();
                boolean approve = resultSet.getBoolean("approve");
                ServiceOrder serviceOrder = new ServiceOrder(
                        user,
                        dateTime
                );
                serviceOrder.setId(resultSet.getInt("id"));
                serviceOrder.setApprove(approve);
                result.add(serviceOrder);
            }

        }
        return result;
    }

    /**
     * Finds buy orders in the repository by date, client name, status, and car ID.
     *
     * @param date       The date of the order (nullable).
     * @param clientName The name of the client (nullable).
     * @param status     The status of the order (nullable).
     * @param carId      The ID of the car (nullable).
     * @return A list of buy orders that match the criteria.
     * @throws RuntimeException If an SQL error occurs during the operation.
     */
    @Override
    public List<BuyOrder> findOrdersBy(LocalDateTime date, String clientName, Boolean status, Integer carId) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM private_schema.t_buy_order WHERE 1=1");

        if (date != null) {
            sqlBuilder.append(" AND local_date_time = ?");
        }
        if (clientName != null) {
            sqlBuilder.append(" AND client_name = ?");
        }
        if (status != null) {
            sqlBuilder.append(" AND approve = ?");
        }
        if (carId != null) {
            sqlBuilder.append(" AND car_id = ?");
        }

        List<BuyOrder> result = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString())) {

            int index = 1;
            if (date != null) {
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(date));
            }
            if (clientName != null) {
                preparedStatement.setString(index++, clientName);
            }
            if (status != null) {
                preparedStatement.setBoolean(index++, status);
            }
            if (carId != null) {
                preparedStatement.setInt(index++, carId);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDateTime dateTime = resultSet.getTimestamp("local_date_time").toLocalDateTime();
                    User user = findUserById(resultSet.getInt("user_id"));
                    Car car = findCarById(resultSet.getInt("car_id"));
                    BuyOrder buyOrder = new BuyOrder(
                            dateTime,
                            user,
                            car
                    );
                    buyOrder.setId(resultSet.getInt("id"));
                    result.add(buyOrder);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error finding orders: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return result;

    }

    @Override
    public boolean updateBuyOrderRequest(Integer id) {
        String updateOrderSql = "UPDATE private_schema.t_buy_order SET approve = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateOrderSql)) {

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.severe("Error updating service order: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a service order to approved status by its ID.
     *
     * @param id the ID of the service order to be updated
     * @return true if the order was successfully updated, false otherwise
     */
    @Override
    public boolean updateCarServiceRequest(Integer id) {
        String updateServiceOrderSql = "UPDATE private_schema.t_service_order SET approve = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateServiceOrderSql)) {

            preparedStatement.setBoolean(1, true);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.severe("Error updating service order: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
