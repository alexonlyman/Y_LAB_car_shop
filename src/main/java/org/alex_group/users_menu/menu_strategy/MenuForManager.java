package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.PrintMenu;

import java.util.Scanner;

public class MenuForManager implements MenuStrategy{

    @Override
    public void display(Scanner scanner) {
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
