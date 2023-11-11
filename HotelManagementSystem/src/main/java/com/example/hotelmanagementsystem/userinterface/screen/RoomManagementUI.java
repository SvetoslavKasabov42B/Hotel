package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RoomManagementUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Room Management");

        Button manageRoomsButton = new Button("Manage Rooms");
        manageRoomsButton.setOnAction(e -> {
            // Handle room management logic here
            System.out.println("Room management screen opened!");
        });

        VBox vBox = new VBox(manageRoomsButton);
        Scene scene = new Scene(vBox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
