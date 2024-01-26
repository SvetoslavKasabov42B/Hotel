package com.hotelmanagementsystem.dal;

import com.hotelmanagementsystem.enums.RoomStatus;
import com.hotelmanagementsystem.enums.RoomType;
import com.hotelmanagementsystem.object.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomQueries {
    private static final String SELECT_ALL_ROOMS_QUERY =
            "SELECT room_number, room_type, status, price FROM hms.Rooms";

    private static final String DELETE_ROOM_QUERY = "DELETE FROM hms.Rooms WHERE room_number = ?";

    private static final String INSERT_ROOM_QUERY = "INSERT INTO hms.Rooms(room_number, room_type, status, price) VALUES(?,?,?, ?)";

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ROOMS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String roomNumber = resultSet.getString("room_number");
                RoomType roomType = RoomType.valueOf(resultSet.getString("room_type"));
                RoomStatus roomStatus = RoomStatus.valueOf(resultSet.getString("status"));
                Double price = resultSet.getDouble("price");

                Room room = new Room(roomNumber, roomType, roomStatus, price);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public static boolean insertRoom(Room room) {


        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROOM_QUERY)) {

            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomType());
            preparedStatement.setString(3, room.getStatus());
            preparedStatement.setString(4, room.getRoomPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean deleteRoomByNumber(String roomNumber) {
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_QUERY)) {

            preparedStatement.setString(1, roomNumber);
            preparedStatement.executeUpdate();

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
