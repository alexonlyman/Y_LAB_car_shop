package org.alex_group;


import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.users_menu.MenuForEachRole;
import org.alex_group.users_menu.MenuForRegisterAndAuth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UserNotFoundEx {
        try (Scanner scanner = new Scanner(System.in)) {

            MenuForRegisterAndAuth.startingApp(scanner);
            MenuForEachRole.redirectionToPersonalAccount(scanner);

        }


        }


    }
