package org.alex_group.service.auth_service;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;
import org.alex_group.repository.userRepo.UserRepository;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.alex_group.model.users.user_context.UserContext.setCurrentUser;

/**
 * Implementation of the AuthService interface that handles user authentication.
 */
public class AuthServiceImpl implements AuthService {
    private final UserRepository repository;
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    /**
     * Constructs an AuthServiceImpl with the given UserRepository.
     *
     * @param repository the UserRepository to be used for user authentication. Must not be {@code null}.
     */
    public AuthServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Authenticates a user based on input from a Scanner.
     *
     * This method prompts the user to enter their login and password, and attempts to authenticate
     * the user using the provided {@link UserRepository}. If the user is found, they are set as
     * the current user and their authentication status is updated.
     *
     * @param scanner the Scanner to read user input. Must not be {@code null}.
     * @throws UserNotFoundEx if the user is not found in the repository.
     * @throws SQLException   if a database access error occurs.
     */

    @Override
    public void auth(Scanner scanner) throws UserNotFoundEx, SQLException {
        scanner.nextLine();
        System.out.println("Вы выбрали авторизацию");
        System.out.println("введите логин");
        String login = scanner.nextLine();
        System.out.println("введите пароль");
        String password = scanner.nextLine();
        User user = repository.findUserByLoginAndPassword(login, password);
        if (user != null) {
            setCurrentUser(user);
            user.setIsAuth(true);
            logger.info("current user " + user);
            System.out.println("пользователь найден");
        } else {
            System.out.println("пользователь не найден");
        }

    }
}
