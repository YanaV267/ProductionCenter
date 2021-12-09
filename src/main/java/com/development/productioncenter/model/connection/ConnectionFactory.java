package com.development.productioncenter.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DATABASE_PROPERTY_FILE = "database.properties";
    private static final String DATABASE_URL_PROPERTY = "url";
    private static final String DATABASE_DRIVER_PROPERTY = "driver";
    private static final String DATABASE_USER_PROPERTY = "user";
    private static final String DATABASE_PASSWORD_PROPERTY = "password";
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Locale locale = new Locale("en", "EN");
        ResourceBundle resourceBundle = ResourceBundle.getBundle(DATABASE_PROPERTY_FILE, locale);
        if (!resourceBundle.containsKey(DATABASE_DRIVER_PROPERTY)) {
            LOGGER.fatal("Error of retrieving driver property value");
            throw new RuntimeException("Error of retrieving driver property value");
        }
        String driver = resourceBundle.getString(DATABASE_DRIVER_PROPERTY);
        if (!resourceBundle.containsKey(DATABASE_URL_PROPERTY)) {
            LOGGER.fatal("Error of retrieving url property value");
            throw new RuntimeException("Error of retrieving url property value");
        }
        URL = resourceBundle.getString(DATABASE_URL_PROPERTY);
        if (!resourceBundle.containsKey(DATABASE_USER_PROPERTY)) {
            LOGGER.fatal("Error of retrieving user property value");
            throw new RuntimeException("Error of retrieving user property value");
        }
        USER = resourceBundle.getString(DATABASE_USER_PROPERTY);
        if (!resourceBundle.containsKey(DATABASE_PASSWORD_PROPERTY)) {
            LOGGER.fatal("Error of retrieving password property value");
            throw new RuntimeException("Error of retrieving password property value");
        }
        PASSWORD = resourceBundle.getString(DATABASE_PASSWORD_PROPERTY);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException exception) {
            LOGGER.fatal("Driver {} wasn't found: {}", driver, exception);
            throw new RuntimeException("Driver " + driver + " wasn't found: ", exception);
        }
    }

    private ConnectionFactory() {
    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
