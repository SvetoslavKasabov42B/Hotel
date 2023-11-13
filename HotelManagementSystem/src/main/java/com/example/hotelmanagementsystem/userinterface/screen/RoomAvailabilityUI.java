package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RoomAvailabilityUI extends Application {

    private TextField reservationNumberField;  // Declare as an instance variable

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Availability");

        // UI components
        DatePicker datePicker = new DatePicker();
        DatePicker datePicker1 = new DatePicker();
        ComboBox<String> bedroomComboBox = new ComboBox<>();
        ComboBox<String> viewComboBox = new ComboBox<>();
        ComboBox<String> availableRooms = new ComboBox<>();
        CheckBox smokingCheckBox = new CheckBox("Smoking Room");
        reservationNumberField = new TextField();  // Initialize here
        Button checkAvailabilityButton = new Button("Check Availability");
        Button makeReservation = new Button("Make Reservation");

        // Populate ComboBox options
        //add sql logic to fill availableRooms on pressed(checkAvailabilityButton)
        bedroomComboBox.getItems().addAll("1","2","3","4","5","6");
        availableRooms.getItems().addAll("101", "102", "103");

        // GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the grid
        gridPane.add(new Label("Check-in Date:"), 0, 0);
        gridPane.add(datePicker, 1, 0);
        gridPane.add(new Label("Check-out Date:"), 0, 1);
        gridPane.add(datePicker1, 1, 1);
        gridPane.add(new Label("Room Type:"), 0, 2);
        gridPane.add(bedroomComboBox, 1, 2);
        gridPane.add(checkAvailabilityButton, 0, 3);
        gridPane.add(new Label("Available Rooms:"), 0, 4);
        gridPane.add(availableRooms, 0, 5);
        gridPane.add(makeReservation, 0, 6);

        // Event handling
        checkAvailabilityButton.setOnAction(e -> {
            // Implement availability check logic based on selected criteria
            // For simplicity, just generate a random reservation number
            reservationNumberField.setText(String.valueOf(generateRandomReservationNumber()));
        });

        makeReservation.setOnAction(e -> {
            //sends you to reservation form with all data from fields
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


}
