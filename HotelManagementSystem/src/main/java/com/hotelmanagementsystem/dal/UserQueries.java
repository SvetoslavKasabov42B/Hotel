package com.hotelmanagementsystem.dal;

import com.hotelmanagementsystem.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserQueries {
    private static final String SELECT_USER_QUERY = "SELECT username FROM hms.Users WHERE username = ? AND password = ?";
    private static final String SELECT_USER_BY_ID_QUERY =
            "SELECT username, password, role FROM hms.Users WHERE username = ?";



    public static User getUserById(String usernameToSet) {
        User user = null;

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_QUERY)) {

            preparedStatement.setString(1, usernameToSet);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    user = new User(username, password, role);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Method to check if the entered username and password match those in the database
    public static boolean isValidUser(String username, String password) {
        try {
            Connection connection = DatabaseConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_QUERY);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Returns true if a user with the provided credentials is found
        } catch (SQLException e) {
            e.printStackTrace();

            return false; // Invalid credentials
        }
    }
    public static boolean insertUser(User user) {
        // Use a SQL INSERT statement to insert the user into the database
        String INSERT_USER_QUERY = "INSERT INTO hms.Users (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());

           int rowsAffected = preparedStatement.executeUpdate();
           return rowsAffected >0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}




