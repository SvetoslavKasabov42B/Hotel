package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RoomReservationsUI extends Application {

    private TextField reservationNumberField;  // Declare as an instance variable

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Reservation");

        // UI components
        DatePicker datePicker = new DatePicker();
        ComboBox<String> bedTypeComboBox = new ComboBox<>();
        ComboBox<String> viewComboBox = new ComboBox<>();
        CheckBox smokingCheckBox = new CheckBox("Smoking Room");
        reservationNumberField = new TextField();  // Initialize here
        Button checkAvailabilityButton = new Button("Check Availability");
        Button confirmReservationButton = new Button("Confirm Reservation");

        // Populate ComboBox options
        bedTypeComboBox.getItems().addAll("Single Bed", "Double Bed", "King Bed");
        viewComboBox.getItems().addAll("Sea View", "City View", "Mountain View");

        // Set default values
        bedTypeComboBox.setValue("Single Bed");
        viewComboBox.setValue("Sea View");

        // GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the grid
        gridPane.add(new Label("Check-in Date:"), 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(new Label("Bed Type:"), 0, 1);
        gridPane.add(bedTypeComboBox, 1, 1);
        gridPane.add(new Label("View:"), 0, 2);
        gridPane.add(viewComboBox, 1, 2);
        gridPane.add(smokingCheckBox, 1, 3);
        gridPane.add(checkAvailabilityButton, 0, 4);
        gridPane.add(new Label("Reservation Number:"), 0, 5);
        gridPane.add(reservationNumberField, 1, 5);
        gridPane.add(confirmReservationButton, 0, 6);

        // Event handling
        checkAvailabilityButton.setOnAction(e -> {
            // Implement availability check logic based on selected criteria
            // For simplicity, just generate a random reservation number
            reservationNumberField.setText(String.valueOf(generateRandomReservationNumber()));
        });

        confirmReservationButton.setOnAction(e -> {
            // Implement reservation confirmation logic
            // For simplicity, just display a confirmation message
            displayConfirmationAlert();
        });

        // Scene
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private int generateRandomReservationNumber() {
        return (int) (Math.random() * 1000) + 1000;
    }

    private void displayConfirmationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Reservation confirmed! Reservation Number: " +
                reservationNumberField.getText());
        alert.showAndWait();
    }
}
