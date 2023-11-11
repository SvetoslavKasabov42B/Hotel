package com.example.hotelmanagementsystem.userinterface.screen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CheckInOutUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Guest Check-In/Check-Out");

        // UI components
        TextField reservationNumberField = new TextField();
        Button expressCheckInButton = new Button("Express Check-In");
        ComboBox<String> roomAssignmentComboBox = new ComboBox<>();
        Button generateKeyCardButton = new Button("Generate Key Card");
        Button checkOutButton = new Button("Check-Out");

        // Populate ComboBox options (Room Assignment)
        roomAssignmentComboBox.getItems().addAll("101", "102", "103", "201", "202", "203");

        // GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the grid
        gridPane.add(new Label("Reservation Number:"), 0, 0);
        gridPane.add(reservationNumberField, 1, 0);
        gridPane.add(expressCheckInButton, 0, 1);
        gridPane.add(new Label("Room Assignment:"), 0, 2);
        gridPane.add(roomAssignmentComboBox, 1, 2);
        gridPane.add(generateKeyCardButton, 0, 3);
        gridPane.add(checkOutButton, 0, 4);

        // Event handling
        expressCheckInButton.setOnAction(e -> {
            // Implement express check-in logic based on reservation number
            // For simplicity, just display a message
            displayInformationAlert("Express Check-In", "Express check-in completed.");
        });

        generateKeyCardButton.setOnAction(e -> {
            // Implement key card generation logic based on selected room
            // For simplicity, just display a message
            displayInformationAlert("Key Card Generation", "Key card generated for Room " +
                    roomAssignmentComboBox.getValue());
        });

        checkOutButton.setOnAction(e -> {
            // Implement check-out logic based on reservation number
            // For simplicity, just display a message
            displayInformationAlert("Check-Out", "Check-out completed.");
        });

        // Scene
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void displayInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

