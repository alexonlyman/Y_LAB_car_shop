package org.alex_group.users_menu.menu_strategy;

import org.alex_group.exception.UserNotFoundEx;

import java.sql.SQLException;
import java.util.Scanner;
/**
 * The {@code MenuForAuth} class implements the {@link MenuStrategy} interface
 * to handle the authentication menu options. It interacts with the authentication service
 * to perform user login operations based on user input.
 */
public class MenuForAuth implements MenuStrategy {
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
        context.getAuthService().auth(scanner);

    }
}
