package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RoomManagementUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Management");

        // Sample data for room status
        ObservableList<String> roomStatusOptions = FXCollections.observableArrayList(
                "Clean", "Dirty", "Under Maintenance", "Vacant"
        );

        // UI components
        ComboBox<String> roomStatusComboBox = new ComboBox<>(roomStatusOptions);
        Button updateRoomStatusButton = new Button("Update Room Status");
        TextArea cleaningScheduleTextArea = new TextArea();
        Button assignCleaningTasksButton = new Button("Assign Cleaning Tasks");
        CheckBox upgradeRequestCheckBox = new CheckBox("Request Room Upgrade");
        Button manageUpgradesButton = new Button("Manage Upgrades");

        // GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the grid
        gridPane.add(new Label("Room Status:"), 0, 0);
        gridPane.add(roomStatusComboBox, 1, 0);
        gridPane.add(updateRoomStatusButton, 0, 1);
        gridPane.add(new Label("Cleaning Schedule:"), 0, 2);
        gridPane.add(cleaningScheduleTextArea, 1, 2);
        gridPane.add(assignCleaningTasksButton, 0, 3);
        gridPane.add(upgradeRequestCheckBox, 0, 4);
        gridPane.add(manageUpgradesButton, 0, 5);

        // Event handling
        updateRoomStatusButton.setOnAction(e -> {
            // Implement room status update logic
            // For simplicity, just display a message
            displayInformationAlert("Room Status Update", "Room status updated to " +
                    roomStatusComboBox.getValue());
        });

        assignCleaningTasksButton.setOnAction(e -> {
            // Implement cleaning schedule assignment logic
            // For simplicity, just display a message
            displayInformationAlert("Assign Cleaning Tasks", "Cleaning tasks assigned.");
        });

        manageUpgradesButton.setOnAction(e -> {
            // Implement room upgrade management logic
            // For simplicity, just display a message
            displayInformationAlert("Manage Upgrades", "Room upgrades managed.");
        });

        // Scene
        Scene scene = new Scene(gridPane, 800, 300);
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
