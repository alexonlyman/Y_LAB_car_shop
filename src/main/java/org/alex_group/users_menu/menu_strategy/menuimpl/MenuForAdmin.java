package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.users_menu.application_context.PrintMenu;
import org.alex_group.users_menu.menu_strategy.MenuStrategy;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * The {@code MenuForAdmin} class implements the {@link MenuStrategy} interface
 * to provide administrative menu options. It handles user input and performs
 * various admin-related operations based on the user's choice.
 */
public class MenuForAdmin implements MenuStrategy {

    /**
     * Displays the admin menu and handles user input to perform
     * administrative operations.
     *
     * @param scanner the {@code Scanner} used to read user input
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void display(Scanner scanner) throws SQLException {
        label:
        while (true) {
            PrintMenu.printMenuForAdmin();
            if (scanner.hasNextInt()) {
                int menu = scanner.nextInt();
                scanner.hasNextLine();
                switch (menu) {
                    case 1:
                        context.getUserService().findAllUsers();
                        break;
                    case 2:
                        context.getOrderService().findAllBuyOrders();
                        break;
                    case 3:
                        context.getOrderMenu().updateCarServiceRequest(scanner);
                        break;
                    case 4:
                        context.getCarMenu().updateCar(scanner);
                        break;
                    case 5:
                        context.getCarMenu().deleteCar(scanner);
                        break;
                    case 6:
                        context.getCarService().findAllCars();
                        break;
                    case 7:
                        context.getOrderMenu().findOrdersBy(scanner);
                        break;
                    case 8:
                        context.getCarMenu().createCar(scanner);
                        break;
                    case 9:
                        context.getUserMenu().updateUser(scanner);
                        break;
                    case 10:
                        context.getOrderMenu().createBuyOrder(scanner);
                        break;
                    case 11:
                        context.getOrderMenu().createServiceOrder(scanner);
                        break;
                    case 12:
                        context.getCarMenu().findCarBy(scanner);
                        break;
                    case 13:
                        break label;

                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
            }
        }
    }
}
