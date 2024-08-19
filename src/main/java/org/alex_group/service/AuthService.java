package org.alex_group.service;

import org.alex_group.exception.UserNotFoundEx;

import java.sql.SQLException;

public interface AuthService {
    boolean auth(String login,String password) throws UserNotFoundEx, SQLException;
}
