package org.alex_group.users_menu.application_context;

import org.alex_group.repository.CarRepository;
import org.alex_group.repository.impl.CarRepositoryImpl;
import org.alex_group.repository.OrderRepo;
import org.alex_group.repository.impl.OrderRepoImpl;
import org.alex_group.repository.UserRepository;
import org.alex_group.repository.impl.UserRepositoryImpl;
import org.alex_group.service.AuthService;
import org.alex_group.service.impl.AuthServiceImpl;
import org.alex_group.service.CarService;
import org.alex_group.service.impl.CarServiceImpl;
import org.alex_group.service.OrderService;
import org.alex_group.service.impl.OrderServiceImpl;
import org.alex_group.service.RegistrationService;
import org.alex_group.service.impl.RegistrationServiceImpl;
import org.alex_group.service.UserService;
import org.alex_group.service.impl.UserServiceImpl;
import org.alex_group.users_menu.menu_strategy.menuimpl.*;

/**
 * Singleton class that provides application-wide access to services and repositories.
 */
public class ApplicationContext {
    private static final ApplicationContext instance = new ApplicationContext();

    private final UserService userService;
    private final CarService carService;
    private final OrderService orderService;
    private final RegistrationService registrationService;
    private final AuthService authService;
    private final MenuForRegister register;
    private final CarMenu carMenu;
    private final OrderMenu orderMenu;
    private final UserMenu userMenu;
    private final MenuForAuth authMenu;
    private final MenuForAdmin menuForAdmin;
    private final MenuForManager menuForManager;
    private final MenuForUser menuForUser;

    /**
     * Private constructor to initialize repositories and services.
     * This ensures the class follows the singleton pattern.
     */
    private ApplicationContext() {
        UserRepository userRepository = new UserRepositoryImpl();
        userService = new UserServiceImpl(userRepository);
        CarRepository carRepository = new CarRepositoryImpl();
        carService = new CarServiceImpl(carRepository);
        OrderRepo orderRepo = new OrderRepoImpl();
        orderService = new OrderServiceImpl(orderRepo);
        registrationService = new RegistrationServiceImpl(userRepository);
        authService = new AuthServiceImpl(userRepository);
        carMenu = new CarMenu(carService);
        orderMenu = new OrderMenu(orderService, carService);
        userMenu = new UserMenu(userService);
        register = new MenuForRegister(registrationService);
        authMenu = new MenuForAuth(authService);
        menuForAdmin = new MenuForAdmin();
        menuForManager = new MenuForManager();
        menuForUser = new MenuForUser();
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

    public CarMenu getCarMenu() {
        return carMenu;
    }

    public OrderMenu getOrderMenu() {
        return orderMenu;
    }

    public UserMenu getUserMenu() {
        return userMenu;
    }

    public MenuForRegister getRegister() {
        return register;
    }

    public MenuForAuth getAuthMenu() {
        return authMenu;
    }

    public MenuForAdmin getMenuForAdmin() {
        return menuForAdmin;
    }

    public MenuForManager getMenuForManager() {
        return menuForManager;
    }

    public MenuForUser getMenuForUser() {
        return menuForUser;
    }
}
