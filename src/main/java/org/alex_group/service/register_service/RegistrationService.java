package org.alex_group.service.register_service;

import java.sql.SQLException;
import java.util.Scanner;

public interface RegistrationService {
    void registerUser(Scanner scanner) throws SQLException;
}