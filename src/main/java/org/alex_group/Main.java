package org.alex_group;


import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.migration.LiquibaseMigration;
import org.alex_group.users_menu.MainMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UserNotFoundEx, SQLException {
        LiquibaseMigration.migrate();
        try (Scanner scanner = new Scanner(System.in)) {
            MainMenu.mainMenu(scanner);

        }

        }
    }