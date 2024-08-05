package org.alex_group.service.orderService;

import org.alex_group.model.cars.Car;
import org.alex_group.model.order.BuyOrder;
import org.alex_group.model.order.ServiceOrder;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.carRepo.CarRepository;
import org.alex_group.repository.orderRepo.OrderRepo;
import org.alex_group.service.auth_service.AuthServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
/**
 * Implementation of the OrderService interface that manages order-related operations.
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());
    private final OrderRepo orderRepo;
    private final CarRepository carRepository;

    User currentUser = UserContext.getCurrentUser();
    LocalDateTime dateTime = LocalDateTime.now();

    /**
     * Constructs an OrderServiceImpl with the given OrderRepo and CarRepository.
     *
     * @param orderRepo the repository for managing orders
     * @param carRepository the repository for managing cars
     */
    public OrderServiceImpl(OrderRepo orderRepo, CarRepository carRepository) {
        this.orderRepo = orderRepo;
        this.carRepository = carRepository;
    }

    /**
     * Creates a new buy order based on user input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     */
    @Override
    public void createBuyOrder(Scanner scanner) {
        scanner.nextLine();
        List<Car> availableCars = carRepository.findAllCars();
        if (availableCars.isEmpty()) {
            System.out.println("Нет доступных автомобилей.");
            return;
        }

        System.out.println("Доступные автомобили:");
        availableCars.forEach(System.out::println);

        System.out.println("Введите номер автомобиля:");
        while (true) {
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                scanner.nextLine();

                Car car = carRepository.reservation(id);
                if (car == null) {
                    System.out.println("Автомобиль с таким номером не найден. Пожалуйста, попробуйте снова.");
                } else {
                    BuyOrder buyOrder = new BuyOrder(dateTime, currentUser, car);
                    orderRepo.createBuyOrder(buyOrder);
                    System.out.println("Заявка на автомобиль " + car + " создана");
                    logger.info("заявка " + buyOrder);
                    break;
                }
            } else {
                System.out.println("Пожалуйста, введите целое число.");
                scanner.next();
            }
        }
    }

    /**
     * Creates a new service order based on user input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     */
    @Override
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
                orderRepo.createServiceOrder(serviceOrder);
                System.out.println("заявка создана");
            } else {
                System.out.println("отмена заявки");
            }
        }

    }

    /**
     * Processes an application based on user input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     * @return true if the application was successfully processed, false otherwise
     */
    @Override
    public boolean applicationProcessing(Scanner scanner) {
        List<BuyOrder> list = findAllBuyOrders();
        int id = scanner.nextInt();
        System.out.println("вы выбрали заявку под номером " + id + " одобрить ее? да/нет");
        scanner.nextLine();
        if (scanner.hasNextLine()) {
            String answer = scanner.nextLine();
            if (answer.equals("да")) {
                for (BuyOrder order : list) {
                    if (order.getId().equals(id)) {
                        order.setApprove(true);
                        System.out.println("заявка одобрена");
                    }
                }
            }
        }
        System.out.println("заявка не одобрена");
        return false;
    }

    /**
     * Finds orders based on user-provided parameters.
     *
     * @param scanner the Scanner to read user input
     * @return a list of orders that match the search criteria
     */
    @Override
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
        return orderRepo.findOrders(date, name, isApproved, carId);
    }

    /**
     * Updates the status of a car service request based on user input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     */
    @Override
    public void updateCarServiceRequest(Scanner scanner) {
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

                boolean updated = orderRepo.updateCarServiceRequest(orderId);
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

    /**
     * Retrieves all buy orders from the repository and prints them.
     *
     * @return a list of all buy orders
     */
    @Override
    public List<BuyOrder> findAllBuyOrders() {
        orderRepo.findAllBuyOrders().forEach(System.out::println);
        return orderRepo.findAllBuyOrders();
    }

    /**
     * Retrieves all service orders from the repository and prints them.
     *
     * @return a list of all service orders
     */
    @Override
    public List<ServiceOrder> findAllServiceOrders() {
        orderRepo.findAllServiceOrders().forEach(System.out::println);
        return orderRepo.findAllServiceOrders();
    }


}
