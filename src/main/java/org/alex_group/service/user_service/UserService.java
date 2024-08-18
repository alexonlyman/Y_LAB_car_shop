package org.alex_group.service.user_service;

import org.alex_group.model.users.User;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public interface UserService {
    void updateUser(Scanner scanner);

    Map<Integer,User> findAllUsers() throws SQLException;


}
