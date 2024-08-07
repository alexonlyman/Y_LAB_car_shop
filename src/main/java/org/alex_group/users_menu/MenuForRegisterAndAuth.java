package org.alex_group.users_menu;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.userRepo.UserRepository;
import org.alex_group.repository.userRepo.UserRepositoryImpl;
import org.alex_group.service.auth_service.AuthService;
import org.alex_group.service.auth_service.AuthServiceImpl;
import org.alex_group.service.register_service.RegistrationService;
import org.alex_group.service.register_service.RegistrationServiceImpl;

import java.util.Scanner;

/**
 * This class manages the registration and authentication processes of users.
 */
public class MenuForRegisterAndAuth {

    /**
     * Starts the application, allowing users to register or authenticate.
     *
     * @param scanner the Scanner instance to read user input
     * @throws UserNotFoundEx if the user is not found during authentication
     */
    public static void startingApp(Scanner scanner) throws UserNotFoundEx {
        UserRepository repository = new UserRepositoryImpl();
        RegistrationService service = new RegistrationServiceImpl(repository);
        AuthService authService = new AuthServiceImpl(repository);



        System.out.println("Добро пожаловать в наш автосалон, для начала вам необходимо зарегистрироваться," +
                " либо пройти авторизацию по логину и паролю, выберите из списка в меню ");

        label:
            while (true) {
                PrintMenu.printRegisterInfo();
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            service.registerUser(scanner);
                            break;
                        case 2:
                            authService.auth(scanner);
                            break;
                        case 3:
                            User currentUser = UserContext.getCurrentUser();
                            if (currentUser == null) {
                                System.out.println("вы не авторизованы");
                                break;
                            } else if (currentUser.getAuth().equals(true)) {
                                break label;
                            }
                    }
                } else {
                    System.out.println("Пожалуйста, введите целое число.");
                    scanner.next();
                }
            }

    }
}
