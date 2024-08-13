package org.alex_group.repository.userRepo;

import org.alex_group.model.users.User;
import org.alex_group.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the UserRepository interface that manages a collection of users.
 */
public class UserRepositoryImpl implements UserRepository {

    /**
     * Registers a new user in the repository.
     *
     * @param user the user to be registered. Must not be {@code null}.
     * @return {@code true} if the user was successfully registered, {@code false} if the user already exists.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public boolean registration(User user) throws SQLException {
        String register = "INSERT INTO private_schema.t_user (firstname,lastname,age,login,password) VALUES (?,?,?,?,?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(register);

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());

            int addedRows = preparedStatement.executeUpdate();
            return addedRows > 0;
        }
    }

    /**
     * Retrieves all users in the repository.
     *
     * @return A map of all users, where the key is the user's ID and the value is the user object.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public Map<Integer,User> findAllUsers() throws SQLException {
        String findAllUsersSQL = "SELECT * FROM private_schema.t_user";
        Map<Integer, User> userMap = new HashMap<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findAllUsersSQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("age"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
                user.setId(resultSet.getInt("id"));
                userMap.put(user.getId(), user);
            }
        }
        return userMap;
    }

    /**
     * Finds a user in the repository by their login and password.
     *
     * @param login    the login of the user. Must not be {@code null}.
     * @param password the password of the user. Must not be {@code null}.
     * @return An {@code Optional} containing the found user, or an empty {@code Optional} if no user was found.
     * @throws SQLException If an SQL error occurs during the operation.
     */
    @Override
    public User findUserByLoginAndPassword(String login, String password) throws SQLException {
        String findUserByLoginAndPassword = "SELECT * FROM private_schema.t_user WHERE login = ? AND password = ?";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(findUserByLoginAndPassword);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

}
