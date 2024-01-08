package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.misc.Guest;
import com.example.hotelmanagementsystem.misc.Reservation;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;


public class CheckInMenu extends Application {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    private Label noReservationsLabel;

    private TextField pinField;
    private ComboBox<Guest> guestComboBox;
    private TextArea commentBox;
    private TableView<Reservation> reservationTable;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Check-In Menu");

        // Layout
        BorderPane borderPane = new BorderPane();
        HBox hbox = createTopMenu();
        reservationTable = createReservationTable();
        borderPane.setTop(hbox);
        borderPane.setCenter(reservationTable);
        noReservationsLabel = new Label("No reservations for the selected guest.");

        VBox vBox = new VBox(reservationTable, noReservationsLabel);
        borderPane.setTop(hbox);
        borderPane.setCenter(vBox);
        // Scene
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private HBox createTopMenu() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 10, 10, 10));

        pinField = new TextField();
        pinField.setPromptText("Enter PIN");

        guestComboBox = new ComboBox<>();
        guestComboBox.setPromptText("Select Guest");

        commentBox = new TextArea();
        commentBox.setPromptText("Enter names of people with the guest");

        Button checkInButton = new Button("Check-In");
        checkInButton.setOnAction(event -> handleCheckIn());

        hbox.getChildren().addAll(pinField, guestComboBox, commentBox, checkInButton);

        // Populate guestComboBox with guests from the database
        populateGuestComboBox();
        guestComboBox.setOnAction(event -> {
            Guest selectedGuest = guestComboBox.getValue();
            if (selectedGuest != null) {
                populateReservationsTable(selectedGuest.getPin());
            }
        });
        return hbox;
    }

    private void populateGuestComboBox() {
        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)) {
            String query = "SELECT * FROM hms.guests";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                ObservableList<Guest> guests = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    Guest guest = new Guest(
                            resultSet.getString("pin"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("gender"),
                            resultSet.getString("email")
                    );
                    guests.add(guest);
                }
                guestComboBox.setItems(guests);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateReservationsTable(String pin) {
        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)) {
            String query = "SELECT reservation_id, room_number, checkin_date, checkout_date FROM hms.reservation WHERE guest_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pin);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<Reservation> reservations = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        Reservation reservation = new Reservation(
                                resultSet.getInt("reservation_id"),
                                pin,
                                resultSet.getInt("room_number"),
                                resultSet.getDate("checkin_date"),
                                resultSet.getDate("checkout_date")
                        );
                        reservations.add(reservation);
                    }

                    // Display reservations or show a message
                    if (!reservations.isEmpty()) {
                        noReservationsLabel.setVisible(false);
                        reservationTable.setItems(reservations);
                    } else {
                        noReservationsLabel.setVisible(true);
                        reservationTable.setItems(FXCollections.emptyObservableList());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private TableView<Reservation> createReservationTable() {
        TableView<Reservation> table = new TableView<>();

        TableColumn<Reservation, Integer> idCol = new TableColumn<>("ID");
        TableColumn<Reservation, Date> checkInDateCol = new TableColumn<>("Check-In Date");
        TableColumn<Reservation, Date> checkOutDateCol = new TableColumn<>("Check-Out Date");
        TableColumn<Reservation, Integer> roomNumberCol = new TableColumn<>("Room Number");

        // Add the columns to the table
        table.getColumns().addAll(idCol, checkInDateCol, checkOutDateCol, roomNumberCol);

        // Set cell value factories for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        checkInDateCol.setCellValueFactory(new PropertyValueFactory<>("checkinDate"));
        checkOutDateCol.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        return table;
    }


    private void handleCheckIn() {
        Guest selectedGuest = guestComboBox.getValue();
        if (selectedGuest == null) {
            showAlert("Please select a guest.");
            return;
        }

        String pin = selectedGuest.getPin();
        String comment = commentBox.getText();

        // Retrieve selected reservation from the table
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation == null) {
            showAlert("Please select a reservation.");
            return;
        }

        // Extract information
        int reservationId = selectedReservation.getReservationId();
        String firstName = selectedGuest.getFirstName();  // Use guest's first name
        String lastName = selectedGuest.getLastName();    // Use guest's last name
        Date checkInDate = (Date) selectedReservation.getCheckinDate();
        Date checkOutDate = (Date) selectedReservation.getCheckoutDate();
        int roomNumber = selectedReservation.getRoomNumber();

        // Perform check-in
        storeCheckInInformation(pin, reservationId, firstName, lastName, checkInDate, checkOutDate, roomNumber,comment);

        // Update reservations table
        populateReservationsTable(pin);

        // Optional: Display a success message
        showAlert("Check-in successful!");

        // Optional: Clear fields or perform other UI updates
        commentBox.clear();
    }

    private void storeCheckInInformation(String pin, int reservationId, String firstName, String lastName,
                                         Date checkInDate, Date checkOutDate, int roomNumber, String comment) {
        try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)) {
            String query = "INSERT INTO hms.checkins (pin, reservation_id, first_name, last_name, "
                    + "checkin_date, checkout_date, room_number,comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, pin);
                preparedStatement.setInt(2, reservationId);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setDate(5, checkInDate);
                preparedStatement.setDate(6, checkOutDate);
                preparedStatement.setInt(7, roomNumber);
                preparedStatement.setString(8, comment);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error storing check-in information. Please try again.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}