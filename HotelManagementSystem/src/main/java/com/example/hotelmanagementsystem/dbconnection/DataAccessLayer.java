package com.example.hotelmanagementsystem.dbconnection;

import com.example.hotelmanagementsystem.misc.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

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

    public List<String> getAllGuests() {
        List<String> guestList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT pin, first_name, last_name FROM hms.guests";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String pin = resultSet.getString("pin");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    guestList.add(pin + " " + firstName + " " + lastName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestList;
    }

    public boolean insertGuest(String pin, String firstName, String lastName, String phoneNumber, String gender, String email) {
        phoneNumber = "+" + phoneNumber;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO hms.guests (pin, first_name, last_name, phone_number, gender,email) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pin);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, phoneNumber);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, email);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteGuest(String pin) {
        String query = "DELETE FROM hms.guests WHERE pin = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            {

                preparedStatement.setString(1, pin);

                int rowsDeleted = preparedStatement.executeUpdate();
                return rowsDeleted > 0;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RoomReservation> getAllReservations() {
        List<RoomReservation> reservations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT r.reservation_id, g.first_name, g.last_name, r.room_number, rm.room_type,r.checkin_date, r.checkout_date, g.pin " +
                    "FROM hms.reservation r " +
                    "JOIN hms.guests g ON r.guest_id = g.pin " +
                    "JOIN hms.rooms rm ON r.room_number = rm.room_number";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservation_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    int roomNumber = resultSet.getInt("room_number");
                    String roomType = resultSet.getString("room_type");
                    Date checkinDate = resultSet.getDate("checkin_date");
                    Date checkoutDate = resultSet.getDate("checkout_date");

                    Room room = new Room(roomNumber,roomType);
                    RoomReservation reservation = new RoomReservation(room, reservationId, firstName, lastName,checkinDate, checkoutDate);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return reservations;
    }
    public List<RoomReservation> getAvailableRooms(Date checkOutDate, Date checkInDate, int roomNumber) {
        List<RoomReservation> availableRooms = new ArrayList<>();

        // Your SQL query to retrieve available rooms
        String query = "SELECT r.room_number, r.room_type " +
                "FROM hms.rooms r " +
                "WHERE r.room_number = ? " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM hms.reservation res " +
                "    WHERE res.room_number = r.room_number " +
                "    AND (res.checkin_date BETWEEN ? AND ? " +
                "         OR res.checkout_date BETWEEN ? AND ?) " +
                ")";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setDate(2, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(checkOutDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(checkOutDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int availableRoomNumber = resultSet.getInt("room_number");
                    String roomType = resultSet.getString("room_type");

                    Room room = new Room(availableRoomNumber, roomType);
                    RoomReservation roomR = new RoomReservation(room);
                    availableRooms.add(roomR);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return availableRooms;
    }


}

