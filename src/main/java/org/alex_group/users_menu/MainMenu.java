package org.alex_group.users_menu;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;
import org.alex_group.model.users.roles.Roles;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.users_menu.application_context.PrintMenu;
import org.alex_group.users_menu.menu_strategy.*;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu implements MenuInit {
    public static void mainMenu(Scanner scanner) throws UserNotFoundEx, SQLException {

        System.out.println("Добро пожаловать в наш автосалон, для начала вам необходимо зарегистрироваться," +
                " либо пройти авторизацию по логину и паролю, выберите из списка в меню ");
        PrintMenu.printRegisterInfo();

        int choice = scanner.nextInt();
        if (choice == 1) {
            context.getRegister().display(scanner);
        }
        if (choice == 2) {
            context.getAuth().display(scanner);
        }

        User currentUser = UserContext.getCurrentUser();

        if (currentUser.getRole().equals(Roles.ADMIN)) {
            context.getMenuForAdmin().display(scanner);
        } else if (currentUser.getRole().equals(Roles.MANAGER)) {
            context.getMenuForManager().display(scanner);
        } else {
            context.getMenuForUser().display(scanner);
        }

    }
}
