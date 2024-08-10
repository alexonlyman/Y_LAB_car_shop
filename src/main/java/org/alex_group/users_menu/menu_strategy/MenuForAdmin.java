package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.PrintMenu;

import java.util.Scanner;

public class MenuForAdmin implements MenuStrategy {
    @Override
    public void display(Scanner scanner) {
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
                        context.getOrderService().updateCarServiceRequest(scanner);
                        break;
                    case 4:
                        context.getCarService().updateCar(scanner);
                        break;
                    case 5:
                        context.getCarService().deleteCar(scanner);
                        break;
                    case 6:
                        context.getCarService().findAllCars();
                        break;
                    case 7:
                        context.getOrderService().findOrdersBy(scanner);
                        break;
                    case 8:
                        context.getCarService().createCar(scanner);
                        break;
                    case 9:
                        context.getUserService().updateUser(scanner);
                        break;
                    case 10:
                        context.getOrderService().createBuyOrder(scanner);
                        break;
                    case 11:
                        context.getOrderService().createServiceOrder(scanner);
                        break;
                    case 12:
                        context.getCarService().findCarBy(scanner);
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
