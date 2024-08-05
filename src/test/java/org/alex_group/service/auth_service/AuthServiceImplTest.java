package org.alex_group.service.auth_service;

import org.alex_group.exception.UserNotFoundEx;
import org.alex_group.model.users.User;
import org.alex_group.model.users.user_context.UserContext;
import org.alex_group.repository.userRepo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Scanner;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserRepository repository;


    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(repository);
    }

    @Test
    void testAuthSuccessful() throws UserNotFoundEx {
        String input = "alex\npassword\n";
        Scanner scanner = new Scanner(input);

        User user = new User("Alex", "Kha", 30, "alex", "password");
        user.setAuth(false);
        when(repository.findUserByLoginAndPassword("alex", "password"))
                .thenReturn(Optional.of(user));

        authService.auth(scanner);


        assertThat(user.getAuth()).isTrue();
        assertThat(UserContext.getCurrentUser()).isEqualTo(user);

        scanner.close();
    }


    @Test
    void testAuthWithException() throws UserNotFoundEx {
        String input = "testuser\ntestpassword\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        when(repository.findUserByLoginAndPassword("testuser", "testpassword"))
                .thenThrow(new RuntimeException("Database error"));

        assertThatThrownBy(() -> authService.auth(scanner))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Database error");
    }
}