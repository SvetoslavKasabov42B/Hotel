package com.example.hotelmanagementsystem.userinterface;


import com.example.hotelmanagementsystem.userinterface.screen.BillingUI;
import com.example.hotelmanagementsystem.userinterface.screen.CheckInOutUI;
import com.example.hotelmanagementsystem.userinterface.screen.RoomManagementUI;
import com.example.hotelmanagementsystem.userinterface.screen.RoomReservationsUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class HMS extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Window");

        Button openReservationsButton = new Button("Open Room Reservations");
        openReservationsButton.setOnAction(e -> openReservationsWindow());

        Button openCheckInOutButton = new Button("Open Check-In/Check-Out");
        openCheckInOutButton.setOnAction(e -> openCheckInOutWindow());

        Button openBillingButton = new Button("Open Billing");
        openBillingButton.setOnAction(e -> openBillingWindow());

        Button openRoomManagementButton = new Button("Open Room Management");
        openRoomManagementButton.setOnAction(e -> openRoomManagementWindow());

        VBox vBox = new VBox(openReservationsButton, openCheckInOutButton, openBillingButton, openRoomManagementButton);
        Scene scene = new Scene(vBox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openReservationsWindow() {
        RoomReservationsUI reservationsUI = new RoomReservationsUI();
        Stage reservationsStage = new Stage();
        reservationsUI.start(reservationsStage);
    }

    private void openCheckInOutWindow() {
        CheckInOutUI checkInOutUI = new CheckInOutUI();
        Stage checkInOutStage = new Stage();
        checkInOutUI.start(checkInOutStage);
    }

    private void openBillingWindow() {
        BillingUI billingUI = new BillingUI();
        Stage billingStage = new Stage();
        billingUI.start(billingStage);
    }

    private void openRoomManagementWindow() {
        RoomManagementUI roomManagementUI = new RoomManagementUI();
        Stage roomManagementStage = new Stage();
        roomManagementUI.start(roomManagementStage);
    }
}