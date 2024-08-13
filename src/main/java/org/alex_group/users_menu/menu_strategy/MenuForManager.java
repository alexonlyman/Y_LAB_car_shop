package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.PrintMenu;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * The {@code MenuForManager} class implements the {@link MenuStrategy} interface
 * to handle the menu options for the manager role. It provides various operations
 * related to users, cars, and orders based on user input.
 */
public class MenuForManager implements MenuStrategy{

    /**
     * Displays the manager menu and handles user input to perform various
     * operations related to users, cars, and orders.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void display(Scanner scanner) throws SQLException {
        label:
        while (true) {
            PrintMenu.printMenuForManager();
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                switch (menu) {
                    case 1:
                        context.getUserService().updateUser(scanner);
                        break;
                    case 2:
                        context.getCarService().createCar(scanner);
                        break;
                    case 3:
                        context.getOrderService().findAllBuyOrders();
                        break;
                    case 4:
                        context.getOrderService().findAllServiceOrders();
                        break;
                    case 5:
                        context.getCarService().updateCar(scanner);
                        break;
                    case 6:
                        context.getCarService().deleteCar(scanner);
                        break;
                    case 7:
                        context.getCarService().findAllCars();
                        break;
                    case 8:
                        context.getOrderService().findOrdersBy(scanner);
                        break;
                    case 9:
                        break label;

                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
            }
        }
    }
}
