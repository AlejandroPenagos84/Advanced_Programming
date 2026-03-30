package org.example.model.conecction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDConecction {

    public static Connection getConnection(String driverUrl, String url, String user, String password) throws SQLException {
        try {
            System.out.println("driverUrl: " + driverUrl);
            Class.forName(driverUrl);
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Database connection failed: " + e.getMessage(), e);
        }
    }
}
