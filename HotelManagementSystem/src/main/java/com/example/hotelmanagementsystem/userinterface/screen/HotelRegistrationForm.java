package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HotelRegistrationForm extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Registration Form");

        // Create the GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Add controls to the GridPane
        Label nameLabel = new Label("Hotel Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter hotel name");
        GridPane.setConstraints(nameField, 1, 0);

        Label addressLabel = new Label("Address:");
        GridPane.setConstraints(addressLabel, 0, 1);

        TextField addressField = new TextField();
        addressField.setPromptText("Enter address");
        GridPane.setConstraints(addressField, 1, 1);

        Label phoneLabel = new Label("Phone:");
        GridPane.setConstraints(phoneLabel, 0, 2);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Enter phone");
        GridPane.setConstraints(phoneField, 1, 2);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 3);

        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");
        GridPane.setConstraints(emailField, 1, 3);

        Button registerButton = new Button("Register Hotel");
        GridPane.setConstraints(registerButton, 1, 4);

        // Add controls to the GridPane
        grid.getChildren().addAll(nameLabel, nameField, addressLabel, addressField, phoneLabel, phoneField, emailLabel, emailField, registerButton);

        // Create the scene
        Scene scene = new Scene(grid, 400, 250);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Set up the event handler for the register button
        //registerButton.setOnAction(event -> );

        // Show the stage
        primaryStage.show();
    }
}