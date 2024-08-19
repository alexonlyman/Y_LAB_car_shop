package org.alex_group.repository.impl;

import org.alex_group.model.users.User;
import org.alex_group.repository.UserRepository;
import org.alex_group.utils.ConnectionUtil;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public Map<Integer, User> findAllUsers() throws SQLException {
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

    @Override
    public boolean updateUser(Integer id, User updatedUser) {
        StringBuilder sqlBuilder = getStringBuilder(updatedUser);
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
            int index = 1;
            if (updatedUser.getFirstname() != null) {
                preparedStatement.setString(index++, updatedUser.getFirstname());
            }
            if (updatedUser.getLastname() != null) {
                preparedStatement.setString(index++, updatedUser.getLastname());
            }
            if (updatedUser.getAge() != null) {
                preparedStatement.setInt(index++, updatedUser.getAge());
            }
            if (updatedUser.getRole() != null) {
                preparedStatement.setString(index++, updatedUser.getRole().name());
            }
            if (updatedUser.getPassword() != null) {
                preparedStatement.setString(index++, updatedUser.getPassword());
            }
            if (updatedUser.getLogin() != null) {
                preparedStatement.setString(index++, updatedUser.getLogin());
            }
            if (updatedUser.getIsAuth() != null) {
                preparedStatement.setBoolean(index++, updatedUser.getIsAuth());
            }

            preparedStatement.setInt(index, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull StringBuilder getStringBuilder(User updatedUser) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE private_schema.t_user SET");

        boolean hasFieldsToUpdate = false;

        if (updatedUser.getFirstname() != null) {
            sqlBuilder.append(" firstname = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getLastname() != null) {
            sqlBuilder.append(" lastname = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getAge() != null) {
            sqlBuilder.append(" age = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getRole() != null) {
            sqlBuilder.append(" role = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getPassword() != null) {
            sqlBuilder.append(" password = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getLogin() != null) {
            sqlBuilder.append(" login = ?,");
            hasFieldsToUpdate = true;
        }
        if (updatedUser.getIsAuth() != null) {
            sqlBuilder.append(" is_auth = ?,");
            hasFieldsToUpdate = true;
        }

        if (!hasFieldsToUpdate) {
            throw new IllegalArgumentException("No fields to update");
        }

        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE id = ?");

        return sqlBuilder;


    }
}
