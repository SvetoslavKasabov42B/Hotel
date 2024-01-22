package com.hotelmanagementsystem.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserQueries {
    private static final String SELECT_USER_QUERY = "SELECT username FROM hms.Users WHERE username = ? AND password = ?";

    // Method to check if the entered username and password match those in the database
    public static boolean isValidUser(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DatabaseConnection.connect();
            preparedStatement = connection.prepareStatement(SELECT_USER_QUERY);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if a user with the provided credentials is found
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources using the close method
            DatabaseConnection.close(preparedStatement);
            DatabaseConnection.close(connection);
        }

        return false; // Invalid credentials
    }
}


