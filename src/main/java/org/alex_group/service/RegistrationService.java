package org.alex_group.service;

import org.alex_group.model.users.User;

import java.sql.SQLException;

public interface RegistrationService {
    void registerUser(User user) throws SQLException;
}