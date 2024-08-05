package org.alex_group.repository.userRepo;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean registration(User user);

    List<User> findAllUsers();

    Optional<User> findUserByLoginAndPassword(String login, String password) throws UserNotFoundEx;


}
