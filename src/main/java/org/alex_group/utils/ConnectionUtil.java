package org.alex_group.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The {@code ConnectionUtil} class provides utility methods for managing database connections.
 * It includes a method to establish a connection to a PostgreSQL database using predefined
 * connection parameters.
 */
public class ConnectionUtil {
   private static String url;
    private static  String username ;
    private  static  String password;
    /**
     * Retrieves a {@link Connection} to the PostgreSQL database using the specified
     * connection URL, username, and password.
     *
     * @return a {@link Connection} object connected to the PostgreSQL database
     * @throws SQLException if a database access error occurs or the URL is invalid
     */
    static{
        try (InputStream is = ConnectionUtil.class.getResourceAsStream("/application.properties")) {
            Properties properties = new Properties();
            if (is == null) {
                throw new IllegalArgumentException("database properties was not find");
            }
            try {
                properties.load(is);
                url = properties.getProperty("jdbc.url");
                username = properties.getProperty("jdbc.username");
                password = properties.getProperty("jdbc.password");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }





}
