package org.alex_group.service.auth_service;

import org.alex_group.exception.UserNotFoundEx;

import java.util.Scanner;

public interface AuthService {
    void auth(Scanner scanner) throws UserNotFoundEx;
}
