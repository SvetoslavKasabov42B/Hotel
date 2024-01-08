package com.example.hotelmanagementsystem.dbconnection;

import com.example.hotelmanagementsystem.misc.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<String> getAllGuests() {
        List<String> guestList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT pin, first_name, last_name, dob FROM hms.guests";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String pin = resultSet.getString("pin");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Date dob = resultSet.getDate("dob");
                    guestList.add(pin + " " + firstName + " " + lastName + " " + dob);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestList;
    }

    public boolean insertGuest(String pin, String firstName, String lastName, String phoneNumber, String gender, String email, LocalDate dob) {
        phoneNumber = "+" + phoneNumber;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO hms.guests (pin, first_name, last_name, phone_number, gender, email, dob) VALUES (?, ?, ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pin);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, phoneNumber);
                preparedStatement.setString(5, gender);
                preparedStatement.setString(6, email);
                preparedStatement.setDate(7, java.sql.Date.valueOf(dob));
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
    public List<RoomReservation> getAvailableRooms(Date checkOutDate, Date checkInDate) {
        List<RoomReservation> availableRooms = new ArrayList<>();

        // Your SQL query to retrieve available rooms
        String query = "SELECT r.room_number, r.room_type " +
                "FROM hms.rooms r " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM hms.reservation res " +
                "    WHERE res.room_number = r.room_number " +
                "    AND (res.checkin_date BETWEEN ? AND ? " +
                "         OR res.checkout_date BETWEEN ? AND ?) " +
                ")";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(checkOutDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(checkOutDate.getTime()));

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

    public List<String> getRoomTypes() {
        List<String> roomTypes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT DISTINCT room_type FROM hms.rooms";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        roomTypes.add(resultSet.getString("room_type"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return roomTypes;
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
    public String getRoomTypeByNumber(int roomNumber) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT room_type FROM hms.rooms WHERE room_number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, roomNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("room_type");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return null;
    }

    public List<RoomReservation> getRoomAvailability(Date checkOutDate, Date checkInDate, int roomNumber) {
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

    public List<RoomReservation> getRoomsDateType(Date checkOutDate, Date checkInDate, String roomType) {
        List<RoomReservation> emptyRooms = new ArrayList<>();

        // Your SQL query to retrieve empty rooms
        String query = "SELECT r.room_number, r.room_type " +
                "FROM hms.rooms r " +
                "WHERE r.room_type = ? " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 " +
                "    FROM hms.reservation res " +
                "    WHERE res.room_number = r.room_number " +
                "    AND (res.checkin_date BETWEEN ? AND ? " +
                "         OR res.checkout_date BETWEEN ? AND ?) " +
                ")";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, roomType);
            preparedStatement.setDate(2, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(3, new java.sql.Date(checkOutDate.getTime()));
            preparedStatement.setDate(4, new java.sql.Date(checkInDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(checkOutDate.getTime()));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int availableRoomNumber = resultSet.getInt("room_number");
                    String roomTypeResult = resultSet.getString("room_type");

                    Room room = new Room(availableRoomNumber, roomTypeResult);
                    RoomReservation emptyRoomReservation = new RoomReservation(room);
                    emptyRooms.add(emptyRoomReservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return emptyRooms;
    }

    //need work
    public List<RoomReservation> getEmptyRoomsAndReservationsTypeToday(String roomType, LocalDate date) {
        List<RoomReservation> emptyRoomsAndReservations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT rm.room_number, rm.room_type, r.checkin_date, r.checkout_date " +
                    "FROM hms.rooms rm " +
                    "LEFT JOIN hms.reservation r ON rm.room_number = r.room_number AND (? BETWEEN r.checkin_date AND r.checkout_date) " +
                    "WHERE rm.room_type = ? AND r.reservation_id IS NULL"; // Added condition for empty rooms

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(date));
                preparedStatement.setString(2, roomType);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int roomNumber = resultSet.getInt("room_number");
                        String type = resultSet.getString("room_type");
                        Date checkinDate = resultSet.getDate("checkin_date");
                        Date checkoutDate = resultSet.getDate("checkout_date");

                        Room room = new Room(roomNumber, roomType);
                        RoomReservation roomReservation = new RoomReservation(room);
                        roomReservation.setCheckinDate(checkinDate);
                        roomReservation.setCheckoutDate(checkoutDate);
                        emptyRoomsAndReservations.add(roomReservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return emptyRoomsAndReservations;
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT room_number, room_type FROM hms.rooms";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    String roomType = resultSet.getString("room_type");

                    Room room = new Room(roomNumber, roomType);
                    rooms.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }


    public void updateRoomStatus(List<Room> rooms, LocalDate startDate, LocalDate endDate) {
        String query = "SELECT room_number FROM hms.reservation " +
                "WHERE " +
                "(checkin_date BETWEEN ? AND ?) OR " +
                "(checkout_date BETWEEN ? AND ?) OR " +
                "(? BETWEEN checkin_date AND checkout_date)";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(2, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(4, java.sql.Date.valueOf(endDate));
            preparedStatement.setDate(5, java.sql.Date.valueOf(startDate));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int occupiedRoomNumber = resultSet.getInt("room_number");
                    // Find the corresponding room and mark it as occupied
                    for (Room room : rooms) {
                        if (room.getRoomNumber() == occupiedRoomNumber) {
                            room.setOccupied(true);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    public void updateRoomStatusToday(List<Room> rooms) {
        String query = "SELECT room_number FROM hms.reservation WHERE (? BETWEEN checkin_date AND checkout_date) ";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            LocalDate today = LocalDate.now();
            preparedStatement.setDate(1, java.sql.Date.valueOf(today));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int occupiedRoomNumber = resultSet.getInt("room_number");
                    // Find the corresponding room and mark it as occupied
                    for (Room room : rooms) {
                        if (room.getRoomNumber() == occupiedRoomNumber) {
                            room.setOccupied(true);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
    // Add these methods to DataAccessLayer class

    public Guest getGuestByPin(String pin) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM hms.guests WHERE pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pin);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Guest(
                                resultSet.getString("pin"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("phone_number"),
                                resultSet.getString("gender"),
                                resultSet.getString("email")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return null;
    }

    public int addGuestReservation(String pin, int reservationId, String firstName, String lastName, String phoneNumber, int roomNumber, String roomType) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO hms.guests_reservations (reservation_id, guest_pin, first_name, last_name, phone_number, room_number, room_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, reservationId);
                preparedStatement.setString(2, pin);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, phoneNumber);
                preparedStatement.setInt(6, roomNumber);
                preparedStatement.setString(7, roomType);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated ID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return -1; // Return -1 if the insertion fails
    }
    public List<RoomReservation> getReservationsByGuestPin(String guestPin) {
        List<RoomReservation> reservations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT r.reservation_id, g.first_name, g.last_name, r.room_number, rm.room_type, r.checkin_date, r.checkout_date " +
                    "FROM hms.reservation r " +
                    "JOIN hms.guests g ON r.guest_id = g.pin " +
                    "JOIN hms.rooms rm ON r.room_number = rm.room_number " +
                    "WHERE g.pin = ? AND r.checkout_date IS NULL"; // Include only active reservations

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, guestPin);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int reservationId = resultSet.getInt("reservation_id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        int roomNumber = resultSet.getInt("room_number");
                        String roomType = resultSet.getString("room_type");
                        Date checkinDate = resultSet.getDate("checkin_date");
                        Date checkoutDate = resultSet.getDate("checkout_date");

                        Room room = new Room(roomNumber, roomType);
                        RoomReservation reservation = new RoomReservation(room, reservationId, firstName, lastName, checkinDate, checkoutDate);
                        reservations.add(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database exception
        }

        return reservations;
    }


    public List<RoomReservation> getReservationsTimeType(Date checkOutDate, Date checkInDate, String roomNumber) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM hms.reservation WHERE room_number = ? AND (checkin_date <= ? AND checkout_date >= ?) OR (checkin_date >= ? AND checkin_date <= ?) OR (checkout_date >= ? AND checkout_date <= ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, roomNumber);
                statement.setDate(2, checkOutDate);
                statement.setDate(3, checkInDate);
                statement.setDate(4, checkInDate);
                statement.setDate(5, checkOutDate);
                statement.setDate(6, checkInDate);
                statement.setDate(7, checkOutDate);

                try (ResultSet resultSet = statement.executeQuery()) {

                    return extractReservations(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<RoomReservation> getReservationsTimeTypeByType(Date checkOutDate, Date checkInDate, String roomType) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM hms.reservation r JOIN hms.rooms rm ON r.room_number = rm.room_number WHERE room_type = ? AND ((checkin_date <= ? AND checkout_date >= ?) OR (checkin_date >= ? AND checkin_date <= ?) OR (checkout_date >= ? AND checkout_date <= ?))";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, roomType);
                statement.setDate(2, checkOutDate);
                statement.setDate(3, checkInDate);
                statement.setDate(4, checkInDate);
                statement.setDate(5, checkOutDate);
                statement.setDate(6, checkInDate);
                statement.setDate(7, checkOutDate);


                try (ResultSet resultSet = statement.executeQuery()) {
                    return extractReservations(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<RoomReservation> extractReservations(ResultSet resultSet) throws SQLException {
        List<RoomReservation> reservations = new ArrayList<>();

        while (resultSet.next()) {
            Room r = new Room(resultSet.getInt("room_number"),resultSet.getString("room_type"));
            RoomReservation reservation = new RoomReservation(r);
            reservation.setReservationId(resultSet.getInt("reservation_id"));
            reservation.setFirstName(resultSet.getString("first_name"));
            reservation.setLastName(resultSet.getString("last_name"));
            reservation.setCheckinDate(resultSet.getDate("checkin_date"));
            reservation.setCheckoutDate(resultSet.getDate("checkout_date"));

            reservations.add(reservation);

        }

        return reservations;
    }
}

