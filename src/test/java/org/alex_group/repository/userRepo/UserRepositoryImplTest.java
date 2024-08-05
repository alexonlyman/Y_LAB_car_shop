package org.alex_group.repository.userRepo;

import org.alex_group.model.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void testRegistrationNewUser() {
        User user = new User("Alex", "Balex", 22, "login", "password");
        assertTrue(userRepository.registration(user));
        assertEquals(1, userRepository.findAllUsers().size());
    }

    @Test
    void testRegistrationDuplicateUser() {
        User user = new User("Alex", "Balex", 22, "login", "password");
        userRepository.registration(user);
        assertFalse(userRepository.registration(user));
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User("Alex", "Balex", 22, "login", "password");
        User user2 = new User("Alexx", "Balex", 22, "логин", "пароль");
        userRepository.registration(user1);
        userRepository.registration(user2);
        assertEquals(2, userRepository.findAllUsers().size());
    }

    @Test
    void testFindUserByLoginAndPassword() {
        User user = new User("Alexx", "Balex", 22, "логин", "пароль");
        userRepository.registration(user);
        Optional<User> foundUser = userRepository.findUserByLoginAndPassword("логин", "пароль");
        assertTrue(foundUser.isPresent());
        assertEquals("логин", foundUser.get().getLogin());
    }
}