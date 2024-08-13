package org.alex_group.repository.userRepo;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserRepository {
    boolean registration(User user) throws SQLException;

    Map<Integer,User> findAllUsers() throws SQLException;

    User findUserByLoginAndPassword(String login, String password) throws UserNotFoundEx, SQLException;
}
