package org.alex_group.service.user_service;

import org.alex_group.model.users.User;

import java.util.List;
import java.util.Scanner;

public interface UserService {
    void updateUser(Scanner scanner);

    List<User> findAllUsers();


}
