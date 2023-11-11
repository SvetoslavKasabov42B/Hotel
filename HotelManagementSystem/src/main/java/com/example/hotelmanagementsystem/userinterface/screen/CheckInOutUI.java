package com.example.hotelmanagementsystem.userinterface.screen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckInOutUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Check-In/Check-Out");

        Button checkInButton = new Button("Check-In");
        checkInButton.setOnAction(e -> {
            // Handle check-in logic here
            System.out.println("Guest checked in!");
        });

        Button checkOutButton = new Button("Check-Out");
        checkOutButton.setOnAction(e -> {
            // Handle check-out logic here
            System.out.println("Guest checked out!");
        });

        VBox vBox = new VBox(checkInButton, checkOutButton);
        Scene scene = new Scene(vBox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
