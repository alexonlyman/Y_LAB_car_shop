package org.alex_group.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code ConnectionUtil} class provides utility methods for managing database connections.
 * It includes a method to establish a connection to a PostgreSQL database using predefined
 * connection parameters.
 */
public class ConnectionUtil {
   private static String url = "jdbc:postgresql://localhost:5432/car_shop";
    private static  String username = "alex";
    private  static  String password = "password";
    /**
     * Retrieves a {@link Connection} to the PostgreSQL database using the specified
     * connection URL, username, and password.
     *
     * @return a {@link Connection} object connected to the PostgreSQL database
     * @throws SQLException if a database access error occurs or the URL is invalid
     */
    public static void setConnectionDetails(String jdbcUrl, String user, String pass) {
        url = jdbcUrl;
        username = user;
        password = pass;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }





}
