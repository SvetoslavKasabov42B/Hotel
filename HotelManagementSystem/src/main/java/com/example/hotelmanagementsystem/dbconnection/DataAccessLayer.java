package com.example.hotelmanagementsystem.dbconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessLayer {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public DataAccessLayer(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;

    }


    public boolean checkAccount(String username, String password) {

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM hms.user WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Returns true if there is at least one result
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately (logging, throw custom exception, etc.)
            return false;
        }
    }

    public boolean createUser(String username, String password, String accountType) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String insertQuery = "INSERT INTO hms.user (username, password, account_type) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, accountType);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String selectQuery = "SELECT username, account_type FROM hms.user";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String accountType = resultSet.getString("account_type");
                    users.add(username + " (" + accountType + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public String getAccountType(String username, String password) {
        String accountType = null;
        String query = "SELECT account_type FROM hms.user WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {


            PreparedStatement preparedStatement = connection.prepareStatement(query);
            {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        accountType = resultSet.getString("account_type");
                    }
                }


            }
            return accountType;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public boolean deleteUser(String username) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM hms.user WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                return preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

