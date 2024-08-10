package org.alex_group.users_menu.application_context;

import org.alex_group.repository.carRepo.CarRepository;
import org.alex_group.repository.carRepo.CarRepositoryImpl;
import org.alex_group.repository.orderRepo.OrderRepo;
import org.alex_group.repository.orderRepo.OrderRepoImpl;
import org.alex_group.repository.userRepo.UserRepository;
import org.alex_group.repository.userRepo.UserRepositoryImpl;
import org.alex_group.service.auth_service.AuthService;
import org.alex_group.service.auth_service.AuthServiceImpl;
import org.alex_group.service.car_service.CarService;
import org.alex_group.service.car_service.CarServiceImpl;
import org.alex_group.service.orderService.OrderService;
import org.alex_group.service.orderService.OrderServiceImpl;
import org.alex_group.service.register_service.RegistrationService;
import org.alex_group.service.register_service.RegistrationServiceImpl;
import org.alex_group.service.user_service.UserService;
import org.alex_group.service.user_service.UserServiceImpl;

/**
 * Singleton class that provides application-wide access to services and repositories.
 */
public class ApplicationContext {
    private static final ApplicationContext instance = new ApplicationContext();

    private final UserRepository userRepository;
    private final UserService userService;
    private final CarRepository carRepository;
    private final CarService carService;
    private final OrderRepo orderRepo;
    private final OrderService orderService;
    private final RegistrationService registrationService;
    private final AuthService authService;

    /**
     * Private constructor to initialize repositories and services.
     * This ensures the class follows the singleton pattern.
     */
    private ApplicationContext() {
        userRepository = new UserRepositoryImpl();
        userService = new UserServiceImpl(userRepository);
        carRepository = new CarRepositoryImpl();
        carService = new CarServiceImpl(carRepository);
        orderRepo = new OrderRepoImpl();
        orderService = new OrderServiceImpl(orderRepo, carRepository);
        registrationService = new RegistrationServiceImpl(userRepository);
        authService = new AuthServiceImpl(userRepository);
    }

    /**
     * Returns the singleton instance of the ApplicationContext.
     *
     * @return the singleton instance
     */
    public static ApplicationContext getInstance() {
        return instance;
    }

    /**
     * Returns the UserService instance.
     *
     * @return the userService
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Returns the CarService instance.
     *
     * @return the carService
     */
    public CarService getCarService() {
        return carService;
    }

    /**
     * Returns the OrderService instance.
     *
     * @return the orderService
     */
    public OrderService getOrderService() {
        return orderService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public AuthService getAuthService() {
        return authService;
    }
}
