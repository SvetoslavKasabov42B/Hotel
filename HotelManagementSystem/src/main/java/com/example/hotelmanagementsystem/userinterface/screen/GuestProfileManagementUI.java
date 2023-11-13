package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuestProfileManagementUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Guest Profile Management");

        // UI components
        TextField nameTextField = new TextField();
        TextField contactTextField = new TextField();
        TextField eMailTextField = new TextField();
        TextField phoneNumber = new TextField();
        Button saveProfileButton = new Button("Save Profile");

        // Layouts
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20));

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().add(saveProfileButton);

        // Add components to the layout
        vBox.getChildren().addAll(
                createLabel("First Name:"), nameTextField,
                createLabel("Surname:"), contactTextField,
                createLabel("E-mail:"), eMailTextField,
                createLabel("Phone number"), phoneNumber,
                buttonBox
        );

        // Style the button
        saveProfileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        // Event handling
        saveProfileButton.setOnAction(e -> {
            // Implement guest profile save logic
            // For simplicity, just display a message
            displayInformationAlert("Save Profile", "Guest profile saved.");
        });

        // Apply styles to the scene
        Scene scene = new Scene(vBox, 800, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;");
        return label;
    }

    private void displayInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

