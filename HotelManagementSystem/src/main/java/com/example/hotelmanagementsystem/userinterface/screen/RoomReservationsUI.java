package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomReservationsUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Reservations");

        Button reserveButton = new Button("Reserve Room");
        reserveButton.setOnAction(e -> {
            // Handle reservation logic here
            System.out.println("Room reserved!");
        });

        VBox vBox = new VBox(reserveButton);
        Scene scene = new Scene(vBox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
