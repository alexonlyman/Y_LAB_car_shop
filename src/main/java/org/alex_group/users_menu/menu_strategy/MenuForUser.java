package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.PrintMenu;

import java.util.Scanner;

public class MenuForUser implements MenuStrategy{

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
