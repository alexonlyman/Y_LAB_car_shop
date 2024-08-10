package org.alex_group.users_menu.menu_strategy;

import org.alex_group.exception.UserNotFoundEx;

import java.util.Scanner;

public class MenuForAuth implements MenuStrategy {
    @Override
    public void display(Scanner scanner) throws UserNotFoundEx {
        context.getAuthService().auth(scanner);

    }
}
