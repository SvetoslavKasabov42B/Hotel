package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Login Page");

        // Create the GridPane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // Add controls to the GridPane
        Label hotelNameLabel = new Label("Hotel Name:");
        GridPane.setConstraints(hotelNameLabel, 0, 0);

        TextField hotelNameField = new TextField();
        hotelNameField.setPromptText("Enter hotel name");
        GridPane.setConstraints(hotelNameField, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        Button registerButton = new Button("Register Hotel");
        GridPane.setConstraints(registerButton, 1, 3);

        // Add controls to the GridPane
        grid.getChildren().addAll(hotelNameLabel, hotelNameField, passwordLabel, passwordField, loginButton, registerButton);

        // Create the scene
        Scene scene = new Scene(grid, 300, 200);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
}
