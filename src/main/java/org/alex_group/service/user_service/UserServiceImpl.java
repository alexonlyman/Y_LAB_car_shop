package org.alex_group.service.user_service;

import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.userRepo.UserRepository;
import org.alex_group.users_menu.application_context.PrintMenu;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
/**
 * Implementation of the UserService interface that handles user-related operations.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a UserServiceImpl with the given UserRepository.
     *
     * @param userRepository the repository for managing users
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates the information of the current user based on user input from a Scanner.
     *
     * @param scanner the Scanner to read user input
     */
    @Override
    public void updateUser(Scanner scanner) {
        scanner.nextLine();
        label:
        while (true) {
            PrintMenu.updateUserInfoMenu();
            if (scanner.hasNextInt()) {
                User currentUser = UserContext.getCurrentUser();
                int menu = scanner.nextInt();
                scanner.nextLine();
                switch (menu) {
                    case 1:
                        System.out.println("введите новое имя");
                        String firstname = scanner.nextLine();
                        currentUser.setFirstname(firstname);
                        System.out.println("имя изменено на " + firstname);
                        break;
                    case 2:
                        System.out.println("введите новую фамилию");
                        String lastname = scanner.nextLine();
                        currentUser.setLastname(lastname);
                        System.out.println("фамилия изменена на " + lastname);
                        break;
                    case 3:
                        System.out.println("введите новый возраст");
                        int age = scanner.nextInt();
                        currentUser.setAge(age);
                        System.out.println("имя изменено на " + age);
                        break;
                    case 4:
                        System.out.println("введите новый логин");
                        String login = scanner.nextLine();
                        currentUser.setFirstname(login);
                        System.out.println("логин изменен на " + login);
                        break;
                    case 5:
                        System.out.println("введите новый пароль");
                        String password = scanner.nextLine();
                        currentUser.setFirstname(password);
                        System.out.println("пароль изменен на " + password);
                        break;

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
     * Finds and prints all users.
     *
     * @return a list of all users
     */
    @Override
    public Map<Integer,User> findAllUsers() throws SQLException {
        Map<Integer, User> map = userRepository.findAllUsers();
        for (Map.Entry<Integer, User> entry : map.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", User: " + entry.getValue());

        }
        return map;
    }
}
