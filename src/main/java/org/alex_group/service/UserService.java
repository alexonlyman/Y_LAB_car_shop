package org.alex_group.service;

import org.alex_group.model.users.User;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {
    void updateUser(Integer id,User user);

    Map<Integer,User> findAllUsers() throws SQLException;


}
