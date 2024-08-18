package org.alex_group.service.register_service;

import org.alex_group.logging.AuditLog;
import org.alex_group.model.users.User;
import org.alex_group.model.users.roles.Roles;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.userRepo.UserRepository;
import org.alex_group.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
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
     *
     * @param scanner the Scanner to read user input
     */
    @Override
    public void registerUser(Scanner scanner) throws SQLException {
        scanner.nextLine();
        System.out.println("Вы выбрали регистрацию");
        System.out.println("введите имя");
        String firstname = scanner.nextLine();
        System.out.println("введите фамилию");
        String lastname = scanner.nextLine();
        System.out.println("введитe возраст");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("придумайте логин");
        String login = scanner.nextLine();
        System.out.println("придумайте пароль");
        String password = scanner.nextLine();
        User user = new User(firstname, lastname, age, login, password);

        System.out.println("если вы сотрудник введите секретный код");
        String secret = scanner.nextLine();

        if (secret.equals("qwerty")) {
            user.setRole(Roles.ADMIN);
        } else if (secret.equals("qw")) {
            user.setRole(Roles.MANAGER);
        } else {
            user.setRole(Roles.USER);
        }

        String insertUserSql = "SELECT id FROM private_schema.t_user WHERE login = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSql)) {
            preparedStatement.setString(1,user.getLogin());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("id");
                    user.setId(generatedId);
                    System.out.println("Регистрация успешна. Ваш ID: " + generatedId);
                }
            }

            boolean isRegistred = repository.registration(user);

            if (isRegistred) {
                System.out.println("вы успешно зарегистрировались");
                user.setIsAuth(true);
                UserContext.setCurrentUser(user);
                AuditLog.log("current user " + user);
                scanner.nextLine();
            } else {
                System.out.println("регистрация неудачна");
            }
        }
    }
}

