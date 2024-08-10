package org.alex_group.users_menu.menu_strategy;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.users_menu.application_context.ApplicationContext;

import java.util.Scanner;

public interface MenuStrategy {
    static final ApplicationContext context = ApplicationContext.getInstance();

    void display(Scanner scanner) throws UserNotFoundEx;
}
