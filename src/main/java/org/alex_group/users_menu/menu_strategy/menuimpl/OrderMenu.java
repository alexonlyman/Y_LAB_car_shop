package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.model.cars.Car;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.service.CarService;
import org.alex_group.service.OrderService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class OrderMenu {
    private static final Logger logger = Logger.getLogger(OrderMenu.class.getName());
    private final OrderService orderService;
    private final CarService carService;


    User currentUser = UserContext.getCurrentUser();
    LocalDateTime dateTime = LocalDateTime.now();

    public OrderMenu(OrderService orderService, CarService carService) {
        this.orderService = orderService;
        this.carService = carService;
    }


    public void createBuyOrder(Scanner scanner) {
        Map<Integer, Car> availableCars = carService.findAllCars();
        if (availableCars.isEmpty()) {
            System.out.println("Нет доступных автомобилей.");
            return;
        }

        System.out.println("Доступные автомобили:");
        availableCars.forEach((id, car) -> System.out.println("айди автомобиля " + id + " автомобиль " + car));
        int id = scanner.nextInt();
        scanner.nextLine();

        Car car = carService.reservation(id);
        if (car == null) {
            System.out.println("Автомобиль с таким номером не найден. Пожалуйста, попробуйте снова.");
        } else {
            BuyOrder buyOrder = new BuyOrder(
                    dateTime, currentUser, car
            );

            logger.info("Заявка " + buyOrder);
        }


    }

    public void createServiceOrder(Scanner scanner) {
        scanner.nextLine();
        System.out.println("""
                Создать заявку на обслуживание автомобиля?
                -да
                -нет
                """);
        if (scanner.hasNextLine()) {
            String answer = scanner.nextLine();
            if (answer.equals("да")) {
                ServiceOrder serviceOrder = new ServiceOrder(currentUser, dateTime);
                orderService.createServiceOrder(serviceOrder);
                System.out.println("заявка создана");
            } else {
                System.out.println("отмена заявки");
            }
        }

    }

    public boolean updateBuyOrderRequest(Scanner scanner) throws SQLException {
        System.out.println("список заявок " + findAllBuyOrders() + " что бы изменить статус выберите id");
        System.out.println("Введите ID заявки, статус которой вы хотите изменить (или 0 для выхода):");
        while (true) {
            try {
                int orderId = scanner.nextInt();
                scanner.nextLine();

                if (orderId == 0) {
                    System.out.println("Выход из обработки заявок.");
                    break;
                }

                boolean updated = orderService.updateBuyOrderRequest(orderId);
                if (updated) {
                    System.out.println("Статус заявки с ID " + orderId + " успешно обновлен.");
                } else {
                    System.out.println("Заявка с ID " + orderId + " не найдена.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите корректный номер ID.");
                scanner.nextLine();
            }

            System.out.println("Введите следующий ID для обновления или 0 для выхода:");
        }
        System.out.println("заявка не одобрена");
        return false;
    }


    public void updateCarServiceRequest(Scanner scanner) throws SQLException {
        System.out.println("список заявок " + findAllServiceOrders() + " что бы изменить статус выберите id");
        System.out.println("Введите ID заявки, статус которой вы хотите изменить (или 0 для выхода):");
        while (true) {
            try {
                int orderId = scanner.nextInt();
                scanner.nextLine();

                if (orderId == 0) {
                    System.out.println("Выход из обработки заявок.");
                    break;
                }

                boolean updated = orderService.updateCarServiceRequest(orderId);
                if (updated) {
                    System.out.println("Статус заявки с ID " + orderId + " успешно обновлен.");
                } else {
                    System.out.println("Заявка с ID " + orderId + " не найдена.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Пожалуйста, введите корректный номер ID.");
                scanner.nextLine();
            }

            System.out.println("Введите следующий ID для обновления или 0 для выхода:");
        }

    }

    public List<BuyOrder> findAllBuyOrders() throws SQLException {
        List<BuyOrder> orders = orderService.findAllBuyOrders();
        System.out.println(orders);
        return orders;
    }

    public List<BuyOrder> findOrdersBy(Scanner scanner) {
        System.out.println("для поиска заявки по параметрам введите время/имя клиента/статус заказа/id автомобиля");
        LocalDateTime date = null;
        System.out.println("Введите дату и время (формат: yyyy-MM-dd HH:mm):");
        String dateString = scanner.nextLine().trim();
        if (!dateString.isEmpty()) {
            try {
                date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты. Поиск будет осуществлен без учета даты.");
            }
        }
        System.out.println("Введите имя клиента:");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = null;

        Boolean isApproved = null;
        System.out.println("Введите статус заказа (одобрен/не одобрен):");
        String statusString = scanner.nextLine().trim().toLowerCase();
        if (!statusString.isEmpty()) {
            if (statusString.equals("одобрен")) {
                isApproved = true;
            } else if (statusString.equals("не одобрен")) {
                isApproved = false;
            } else {
                System.out.println("Неверный статус. Поиск будет осуществлен без учета статуса.");
            }
        }

        Integer carId = null;
        System.out.println("Введите ID автомобиля:");
        String carIdString = scanner.nextLine().trim();
        if (!carIdString.isEmpty()) {
            try {
                carId = Integer.parseInt(carIdString);
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ID. Поиск будет осуществлен без учета ID автомобиля.");
            }
        }
        return orderService.findOrdersBy(date, name, isApproved, carId);

    }

    public List<ServiceOrder> findAllServiceOrders() throws SQLException {
        List<ServiceOrder> orders = orderService.findAllServiceOrders();
        System.out.println(orders);
        return orders;


    }
}
