package com.hotelmanagementsystem.dal;

import com.hotelmanagementsystem.object.Reservation;
import com.hotelmanagementsystem.object.Room;
import com.hotelmanagementsystem.object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationQueries {

    private static final String SELECT_ALL_RESERVATIONS_QUERY =
            "SELECT contact_number, user_id, room_number, start_date, end_date, guest_name, total_price   FROM hms.Reservations";


    // Other reservation-related queries can be added as needed

    public static List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESERVATIONS_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String contactNumber = resultSet.getString("contact_number");
                String userId = resultSet.getString("user_id");
                String roomId = resultSet.getString("room_number");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String guestName = resultSet.getString("guest_name");
                double totalPrice = resultSet.getDouble("total_price");


                Reservation reservation = new Reservation(contactNumber,userId, roomId, startDate, endDate, guestName, totalPrice);
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
