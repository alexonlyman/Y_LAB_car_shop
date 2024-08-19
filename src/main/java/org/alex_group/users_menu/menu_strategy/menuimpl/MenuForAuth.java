package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.service.AuthService;
import org.alex_group.users_menu.MainMenu;
import org.alex_group.users_menu.menu_strategy.MenuStrategy;

import java.sql.SQLException;
import java.util.Scanner;
/**
 * The {@code MenuForAuth} class implements the {@link MenuStrategy} interface
 * to handle the authentication menu options. It interacts with the authentication service
 * to perform user login operations based on user input.
 */
public class MenuForAuth implements MenuStrategy {
    private final AuthService authService;

    public MenuForAuth(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Displays the authentication menu and handles user input to perform
     * user authentication.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @throws UserNotFoundEx if the user is not found during authentication
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void display(Scanner scanner) throws UserNotFoundEx, SQLException {
        scanner.nextLine();
        System.out.println("Вы выбрали авторизацию");
        System.out.println("введите логин");
        String login = scanner.nextLine();
        System.out.println("введите пароль");
        String password = scanner.nextLine();
        boolean isAuth = authService.auth(login, password);
        if (!isAuth) {
            System.out.println("вы не авторизованы,попробуйте либо зарегистрироваться либо пройти авторизацию заново");
            MainMenu.mainMenu(scanner);
        }

    }
}
