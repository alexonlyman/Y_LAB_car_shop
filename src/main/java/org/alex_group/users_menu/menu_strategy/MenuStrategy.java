package org.alex_group.users_menu.menu_strategy;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.users_menu.application_context.ApplicationContext;

import java.sql.SQLException;
import java.util.Scanner;
/**
 * The {@code MenuStrategy} interface defines a strategy for displaying menus within
 * the application. Each implementing class provides a specific menu and its corresponding
 * functionality based on user input.
 */
public interface MenuStrategy {
    static final ApplicationContext context = ApplicationContext.getInstance();

    void display(Scanner scanner) throws UserNotFoundEx, SQLException;
}
