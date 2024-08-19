package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.logging.AuditLog;
import org.alex_group.model.users.User;
import org.alex_group.model.users.roles.Roles;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.service.RegistrationService;
import org.alex_group.users_menu.menu_strategy.MenuStrategy;
import org.alex_group.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
/**
 * The {@code MenuForRegister} class implements the {@link MenuStrategy} interface
 * to handle the menu options for user registration. It provides the functionality
 * to register a new user based on user input.
 */
public class MenuForRegister implements MenuStrategy {
    private final RegistrationService registrationService;

    public MenuForRegister(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * Displays the registration menu and handles user input to perform
     * user registration.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void display(Scanner scanner) throws SQLException {

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
            preparedStatement.setString(1, user.getLogin());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int generatedId = resultSet.getInt("id");
                    user.setId(generatedId);
                    System.out.println("Регистрация успешна. Ваш ID: " + generatedId);
                }
            }
            registrationService.registerUser(user);

            System.out.println("вы успешно зарегистрировались");
            user.setIsAuth(true);
            UserContext.setCurrentUser(user);
            AuditLog.log("current user " + user);
            scanner.nextLine();


        }
    }
}
