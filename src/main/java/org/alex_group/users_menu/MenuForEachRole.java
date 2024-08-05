package org.alex_group.users_menu;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;
import org.alex_group.model.users.roles.Roles;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.users_menu.application_context.ApplicationContext;

import java.util.Scanner;

/**
 * This class manages redirection to different user menus based on their role.
 */
public class MenuForEachRole {
    private static final ApplicationContext contex = ApplicationContext.getInstance();

    /**
     * Redirects users to the appropriate menu based on their role.
     *
     * @param scanner the Scanner instance to read user input
     * @throws UserNotFoundEx if the user is not found
     */
    public static void redirectionToPersonalAccount(Scanner scanner) throws UserNotFoundEx {
        User currentUser = UserContext.getCurrentUser();
        if (currentUser.getRole().equals(Roles.USER)) {
            userMenu(scanner);
        } else if (currentUser.getRole().equals(Roles.MANAGER)) {
            managerMenu(scanner);
        } else {
            adminMenu(scanner);
        }

    }

    /**
     * Displays the menu for regular users and handles their actions.
     *
     * @param scanner the Scanner instance to read user input
     * @throws UserNotFoundEx if the user is not found
     */
    private static void userMenu(Scanner scanner) throws UserNotFoundEx {
            label:
            while (true) {
                PrintMenu.printMenuForUser();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            contex.getUserService().updateUser(scanner);
                            break;
                        case 2:
                            contex.getCarService().findAllCars();
                            break;
                        case 3:
                            contex.getOrderService().createBuyOrder(scanner);
                            break;
                        case 4:
                            contex.getOrderService().createServiceOrder(scanner);
                            break;
                        case 5:
                            contex.getCarService().findCarBy(scanner);
                        case 6:
                            break label;
                    }
                } else {
                    System.out.println("Пожалуйста, введите целое число.");
                    scanner.next();
                }
            }
    }

    /**
     * Displays the menu for managers and handles their actions.
     *
     * @param scanner the Scanner instance to read user input
     */
    private static void managerMenu(Scanner scanner) {
            label:
            while (true) {
                PrintMenu.printMenuForManager();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            contex.getUserService().updateUser(scanner);
                            break;
                        case 2:
                            contex.getCarService().createCar(scanner);
                            break;
                        case 3:
                            contex.getOrderService().findAllBuyOrders();
                            break;
                        case 4:
                            contex.getOrderService().findAllServiceOrders();
                            break;
                        case 5:
                            contex.getCarService().updateCar(scanner);
                            break;
                        case 6:
                            contex.getCarService().deleteCar(scanner);
                            break;
                        case 7:
                            contex.getCarService().findAllCars();
                            break;
                        case 8:
                            contex.getOrderService().findOrdersBy(scanner);
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

    /**
     * Displays the menu for admins and handles their actions.
     *
     * @param scanner the Scanner instance to read user input
     */
    private static void adminMenu(Scanner scanner) {
            label:
            while (true) {
               PrintMenu.printMenuForAdmin();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    scanner.hasNextLine();
                    switch (menu) {
                        case 1:
                            contex.getUserService().findAllUsers();
                            break;
                        case 2:
                            contex.getOrderService().findAllBuyOrders();
                            break;
                        case 3:
                            contex.getOrderService().updateCarServiceRequest(scanner);
                            break;
                        case 4:
                            contex.getCarService().updateCar(scanner);
                            break;
                        case 5:
                            contex.getCarService().deleteCar(scanner);
                            break;
                        case 6:
                            contex.getCarService().findAllCars();
                            break;
                        case 7:
                            contex.getOrderService().findOrdersBy(scanner);
                            break;
                        case 8:
                            contex.getCarService().createCar(scanner);
                            break;
                        case 9:
                            contex.getUserService().updateUser(scanner);
                            break;
                        case 10:
                            contex.getOrderService().createBuyOrder(scanner);
                            break;
                        case 11:
                            contex.getOrderService().createServiceOrder(scanner);
                            break;
                        case 12:
                            contex.getCarService().findCarBy(scanner);
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
