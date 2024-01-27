package com.hotelmanagementsystem.userinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReservationMenu {
    public void showReservationMenu() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelmanagementsystem/views/ReservationManagerScreen.fxml"));
            Parent root = loader.load();

            // Set up the stage and scene
            Stage stage = new Stage();
            stage.setTitle("Reservation Manager");
            Scene scene = new Scene(root);

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
