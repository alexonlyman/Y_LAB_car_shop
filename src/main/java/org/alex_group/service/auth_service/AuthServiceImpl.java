package org.alex_group.service.auth_service;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.repository.userRepo.UserRepository;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;

import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;
/**
 * Implementation of the AuthService interface that handles user authentication.
 */
public class AuthServiceImpl implements AuthService {
    UserRepository repository;
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    /**
     * Constructs an AuthServiceImpl with the given UserRepository.
     *
     * @param repository the UserRepository to be used for user authentication
     */
    public AuthServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * Authenticates a user based on input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     * @throws UserNotFoundEx if the user is not found
     */
    @Override
    public void auth(Scanner scanner) throws UserNotFoundEx {
        System.out.println("Вы выбрали авторизацию");
        System.out.println("введите логин");
        String login = scanner.nextLine();
        System.out.println("введите пароль");
        String password = scanner.nextLine();
        Optional<User> user = repository.findUserByLoginAndPassword(login, password);
        if (user.isPresent()) {
            User currentUser = user.get();
            currentUser.setAuth(true);
            UserContext.setCurrentUser(currentUser);
            logger.info("current user " + currentUser);
            System.out.println("пользователь найден");
        } else {
            System.out.println("пользователь не найден");
        }

    }
}
