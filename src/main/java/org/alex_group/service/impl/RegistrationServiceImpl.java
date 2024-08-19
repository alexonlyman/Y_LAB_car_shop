package org.alex_group.service.impl;

import org.alex_group.model.users.User;
import org.alex_group.repository.UserRepository;
import org.alex_group.service.RegistrationService;

import java.sql.SQLException;
/**
 * Implementation of the RegistrationService interface that handles user registration.
 */
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository repository;

    /**
     * Constructs a RegistrationServiceImpl with the given UserRepository.
     *
     * @param repository the repository for managing users
     */
    public RegistrationServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Registers a new user based on user input from a Scanner.
     */
    @Override
    public void registerUser(User user) throws SQLException {

        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        int age = user.getAge();
        String login = user.getLogin();
        String password = user.getPassword();
        User registredUser = new User(firstname, lastname, age, login, password);
        repository.registration(registredUser);


    }
}


