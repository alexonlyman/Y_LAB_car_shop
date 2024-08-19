package org.alex_group.users_menu.menu_strategy.menuimpl;

import org.alex_group.model.users.User;
import org.alex_group.model.users.roles.Roles;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.service.UserService;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class UserMenu {
    private final UserService userService;
    User currentUser = UserContext.getCurrentUser();

    public UserMenu(UserService userService) {
        this.userService = userService;
    }

    public void updateUser(Scanner scanner) throws SQLException {
        int id = currentUser.getId();
        User userToUpdate = null;
        for (Map.Entry<Integer, User> entry : userService.findAllUsers().entrySet()) {
            if (entry.getKey() == id) {
                userToUpdate = entry.getValue();
            }
        }
        if (userToUpdate == null) {
            System.out.println("Пользователь с ID " + id + " не найден.");
            return;
        }

        System.out.println("Текущая информация о пользователе:");
        System.out.println(userToUpdate);

        System.out.println("Введите новое имя пользователя (или Enter для пропуска):");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            userToUpdate.setFirstname(input);
        }

        System.out.println("Введите новую фамилию пользователя (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            userToUpdate.setLastname(input);
        }

        System.out.println("Введите новый возраст пользователя (или 0 для пропуска):");
        int age = scanner.nextInt();
        if (age != 0) {
            userToUpdate.setAge(age);
        }
        scanner.nextLine();

        System.out.println("Введите новую роль пользователя (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            userToUpdate.setRole(Roles.valueOf(input.toUpperCase()));
        }

        System.out.println("Введите новый логин пользователя (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            userToUpdate.setLogin(input);
        }

        System.out.println("Введите новый пароль пользователя (или Enter для пропуска):");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            userToUpdate.setPassword(input);
        }

        System.out.println("Пользователь успешно обновлен.");
        userService.updateUser(id, userToUpdate);
        System.out.println("Обновленная информация:");
        System.out.println(userToUpdate);
    }

    public Map<Integer,User> findAllUsers() throws SQLException {
        Map<Integer, User> map = userService.findAllUsers();
        for (Map.Entry<Integer, User> entry : map.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", User: " + entry.getValue());

        }
        return map;
    }
}
