package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import com.example.hotelmanagementsystem.misc.Guest;
import com.example.hotelmanagementsystem.misc.RoomReservation;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.util.List;

public class CheckInMenu extends Application {

    private final DataAccessLayer dal;
    private Guest selectedGuest;
private TextField pinTextField;
    public CheckInMenu() {
        this.dal = new DataAccessLayer("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Check-In Menu");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Create UI components
        Label pinLabel = new Label("Guest PIN:");
        pinTextField = new TextField();
        Button checkInButton = new Button("Check-In");
        Button registerGuestButton = new Button("Register Guest");

        // Add UI components to the grid
        grid.add(pinLabel, 0, 0);
        grid.add(pinTextField, 1, 0);
        grid.add(checkInButton, 2, 0);
        grid.add(registerGuestButton, 3, 0);

        // Set actions for buttons
        checkInButton.setOnAction(e -> checkIn());
        registerGuestButton.setOnAction(e -> registerGuest());

        Scene scene = new Scene(grid, 700, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkIn() {
        String pin = pinTextField.getText();

        Platform.runLater(() -> {
            if (!pin.isEmpty()) {
                Guest guest = dal.getGuestByPin(pin);
                if (guest != null) {
                    // Guest exists, show reservation details
                    selectedGuest = guest;
                    showReservationDetails();
                } else {
                    // Guest does not exist, prompt for registration
                    showAlert("Guest Not Found", "No guest found with the provided PIN. Please register the guest.");
                }
            } else {
                showAlert("Invalid Input", "Please enter a valid Guest PIN.");
            }
        });
    }


    private void showReservationDetails() {
        List<RoomReservation> reservations = dal.getReservationsByGuestPin(selectedGuest.getPin());

        // Create a new stage for reservation details
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Reservation Details");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Display reservation details in the grid
        for (int i = 0; i < reservations.size(); i++) {
            RoomReservation reservation = reservations.get(i);

            Label roomLabel = new Label("Room Number: " + reservation.getRoomNumber());
            Label checkInLabel = new Label("Check-In Date: " + reservation.getCheckinDate());
            Label checkOutLabel = new Label("Check-Out Date: " + reservation.getCheckoutDate());

            // Add reservation details to the grid
            grid.add(roomLabel, 0, i);
            grid.add(checkInLabel, 1, i);
            grid.add(checkOutLabel, 2, i);
        }

        // Create a new scene and set it to the stage
        Scene detailsScene = new Scene(grid, 400, 200);
        detailsStage.setScene(detailsScene);
        detailsStage.show();
    }

    private void registerGuest() {
        // Open the GuestManager menu for registration
        GuestManager guestManagerMenu = new GuestManager(dal);
        guestManagerMenu.start(new Stage());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
