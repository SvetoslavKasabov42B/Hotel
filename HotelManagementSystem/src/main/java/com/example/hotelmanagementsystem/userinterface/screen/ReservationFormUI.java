package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ReservationFormUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Reservation Form");

        // Create the GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Add controls to the GridPane
        Label guestLabel = new Label("Guest Name:");
        GridPane.setConstraints(guestLabel, 0, 0);

        TextField guestNameField = new TextField();
        guestNameField.setPromptText("Enter guest name");
        GridPane.setConstraints(guestNameField, 1, 0);

        Label guestPhoneLabel = new Label("Guest Name:");
        GridPane.setConstraints(guestLabel, 0, 0);

        TextField guestPhoneField = new TextField();
        guestNameField.setPromptText("Enter guest name");
        GridPane.setConstraints(guestNameField, 1, 0);

        Label roomLabel = new Label("Room Number:");
        GridPane.setConstraints(roomLabel, 0, 1);

        TextField roomNumberField = new TextField();
        roomNumberField.setPromptText("filled from previous form");
        GridPane.setConstraints(roomNumberField, 1, 1);

        Label checkInLabel = new Label("Check-in Date:");
        GridPane.setConstraints(checkInLabel, 0, 2);

        DatePicker checkInDatePicker = new DatePicker();
        GridPane.setConstraints(checkInDatePicker, 1, 2);

        Label checkOutLabel = new Label("Check-out Date:");
        GridPane.setConstraints(checkOutLabel, 0, 3);

        DatePicker checkOutDatePicker = new DatePicker();
        GridPane.setConstraints(checkOutDatePicker, 1, 3);

        Button reserveButton = new Button("Reserve Room");
        GridPane.setConstraints(reserveButton, 1, 4);

        // Add controls to the GridPane
        grid.getChildren().addAll(guestLabel, guestNameField, roomLabel, roomNumberField,
                checkInLabel, checkInDatePicker, checkOutLabel, checkOutDatePicker, reserveButton);

        // Create the scene
        Scene scene = new Scene(grid, 400, 250);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set up the event handler for the reserve button
        //reserveButton.setOnAction(event -> reserveRoom(guestNameField.getText(), roomNumberField.getText(),
        //checkInDatePicker.getValue(), checkOutDatePicker.getValue()));

        // Show the stage
        primaryStage.show();
    }
}