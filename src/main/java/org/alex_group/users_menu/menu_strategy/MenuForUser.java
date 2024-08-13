package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.PrintMenu;

import java.util.Scanner;

/**
 * The {@code MenuForUser} class implements the {@link MenuStrategy} interface
 * to handle the menu options available to a regular user. It provides functionality
 * for users to perform various operations through the menu.
 */
public class MenuForUser implements MenuStrategy{

    /**
     * Displays the user menu and handles user input to perform
     * various user-related operations.
     *
     * @param scanner the {@code Scanner} used to read user input
     */
    @Override
    public void display(Scanner scanner) {
        label:
        while (true) {
            PrintMenu.printMenuForUser();
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1:
                        context.getUserService().updateUser(scanner);
                        break;
                    case 2:
                        context.getCarService().findAllCars();
                        break;
                    case 3:
                        context.getOrderService().createBuyOrder(scanner);
                        break;
                    case 4:
                        context.getOrderService().createServiceOrder(scanner);
                        break;
                    case 5:
                        context.getCarService().findCarBy(scanner);
                    case 6:
                        break label;
                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
            }
        }
    }
}
