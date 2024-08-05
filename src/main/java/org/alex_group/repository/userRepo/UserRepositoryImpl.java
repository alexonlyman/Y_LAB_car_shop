package org.alex_group.repository.userRepo;

import org.alex_group.model.users.User;

import java.util.*;
/**
 * Implementation of the UserRepository interface that manages a collection of users.
 */
public class UserRepositoryImpl implements UserRepository {
    private final Set<User> users = new HashSet<>();


    /**
     * Registers a new user in the repository.
     *
     * @param user the user to be registered
     * @return true if the user was successfully registered, false if the user already exists
     */
    @Override
    public boolean registration(User user) {
        return users.add(user);
    }

    /**
     * Retrieves all users in the repository.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAllUsers() {
        return users.stream().toList();
    }

    /**
     * Finds a user in the repository by their login and password.
     *
     * @param login    the login of the user
     * @param password the password of the user
     * @return an Optional containing the found user, or an empty Optional if no user was found
     */
    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(u -> login.equals(u.getLogin()) && password.equals(u.getPassword()))
                .findFirst();
    }


}
