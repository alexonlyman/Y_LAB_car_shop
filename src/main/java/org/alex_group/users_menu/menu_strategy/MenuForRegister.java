package org.alex_group.users_menu.menu_strategy;

import java.sql.SQLException;
import java.util.Scanner;
/**
 * The {@code MenuForRegister} class implements the {@link MenuStrategy} interface
 * to handle the menu options for user registration. It provides the functionality
 * to register a new user based on user input.
 */
public class MenuForRegister implements MenuStrategy{

    /**
     * Displays the registration menu and handles user input to perform
     * user registration.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void display(Scanner scanner) throws SQLException {
        context.getRegistrationService().registerUser(scanner);

    }
}
