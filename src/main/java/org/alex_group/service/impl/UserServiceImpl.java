package org.alex_group.service.impl;

import org.alex_group.model.users.User;
import org.alex_group.repository.UserRepository;
import org.alex_group.service.UserService;

import java.sql.SQLException;
import java.util.Map;
/**
 * Implementation of the UserService interface that handles user-related operations.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a UserServiceImpl with the given UserRepository.
     *
     * @param userRepository the repository for managing users
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates the information of the current user based on user input from a Scanner.
     *
     */
    @Override
    public void updateUser(Integer id,User user) {
        userRepository.updateUser(id, user);
    }

    /**
     * Finds and prints all users.
     *
     *
     */
    @Override
    public Map<Integer,User> findAllUsers() throws SQLException {
        return userRepository.findAllUsers();
    }
}
