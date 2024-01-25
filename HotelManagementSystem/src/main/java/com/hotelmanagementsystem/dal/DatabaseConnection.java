package com.hotelmanagementsystem.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    // JDBC URL, username, and password of PostgreSQL server
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    // Method to establish a database connection
    public static Connection connect() {
        try {
            // Register the JDBC driver
            Class.forName("org.postgresql.Driver");

            // Open a connection
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // Method to close the database connection

}
