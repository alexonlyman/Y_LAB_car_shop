package org.alex_group.users_menu.menu_strategy;

import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;

import java.util.Scanner;

public class MenuForRegister implements MenuStrategy{
    @Override
    public void display(Scanner scanner) {
        context.getRegistrationService().registerUser(scanner);
        User currentUser = UserContext.getCurrentUser();

    }
}
