package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Menu");

        // Create buttons for each menu item with different colors
        Button accountManagerBtn = createStyledButton("ACCOUNT MANAGER", "#4CAF50");
        Button guestManagerBtn = createStyledButton("GUEST MANAGER", "#2196F3");
        Button reservationManagerBtn = createStyledButton("RESERVATION MANAGER", "#FF9800");
        Button housekeepingBtn = createStyledButton("HOUSEKEEPING", "#9C27B0");
        Button rackRoomBtn = createStyledButton("RACK ROOM", "#FF5722");
        Button checkInBtn = createStyledButton("CHECK IN", "#795548");
        Button managerReportsBtn = createStyledButton("MANAGER REPORTS", "#607D8B");

        // Set action for each button (you can replace the lambda expressions with your own logic)
        accountManagerBtn.setOnAction(e -> System.out.println("Clicked ACCOUNT MANAGER"));
        guestManagerBtn.setOnAction(e -> System.out.println("Clicked GUEST MANAGER"));
        reservationManagerBtn.setOnAction(e -> System.out.println("Clicked RESERVATION MANAGER"));
        housekeepingBtn.setOnAction(e -> System.out.println("Clicked HOUSEKEEPING"));
        rackRoomBtn.setOnAction(e -> System.out.println("Clicked RACK ROOM"));
        checkInBtn.setOnAction(e -> System.out.println("Clicked CHECK IN"));
        managerReportsBtn.setOnAction(e -> System.out.println("Clicked MANAGER REPORTS"));

        // Create HBox containers for left and right sides
        HBox leftBox = new HBox(20, accountManagerBtn, guestManagerBtn, reservationManagerBtn);
        HBox rightBox = new HBox(20, housekeepingBtn, rackRoomBtn, checkInBtn, managerReportsBtn);

        // Set padding for both boxes
        leftBox.setPadding(new Insets(20));
        rightBox.setPadding(new Insets(20));

        // Align the boxes to the center
        leftBox.setAlignment(Pos.CENTER);
        rightBox.setAlignment(Pos.CENTER);

        // Create a VBox to hold the left and right boxes
        VBox vbox = new VBox(leftBox, rightBox);

        // Set HBox and VBox to grow according to the available space
        HBox.setHgrow(leftBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(rightBox, javafx.scene.layout.Priority.ALWAYS);
        VBox.setVgrow(vbox, javafx.scene.layout.Priority.ALWAYS);

        // Set up the scene
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold;"
        );
        return button;
    }
}

