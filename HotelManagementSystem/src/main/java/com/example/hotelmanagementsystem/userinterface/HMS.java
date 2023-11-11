package com.example.hotelmanagementsystem.userinterface;


import com.example.hotelmanagementsystem.userinterface.screen.BillingUI;
import com.example.hotelmanagementsystem.userinterface.screen.CheckInOutUI;
import com.example.hotelmanagementsystem.userinterface.screen.RoomManagementUI;
import com.example.hotelmanagementsystem.userinterface.screen.RoomReservationsUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HMS extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Management System");

        // Create a VBox for the main layout
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(20);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add a stylish title
        Text titleText = new Text("Welcome to Hotel Management System");
        titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Create buttons for each module
        Button openReservationsButton = createStyledButton("Room Reservations");
        Button openCheckInOutButton = createStyledButton("Check-In/Check-Out");
        Button openBillingButton = createStyledButton("Billing and Invoicing");
        Button openRoomManagementButton = createStyledButton("Room Management");

        // Add buttons and title to the main layout
        mainLayout.getChildren().addAll(titleText, openReservationsButton, openCheckInOutButton, openBillingButton, openRoomManagementButton);

        // Set the main layout as the scene content
        Scene scene = new Scene(mainLayout, 600, 400);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private Button createStyledButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setMinWidth(200);
        button.setMinHeight(40);
        button.setOnAction(e -> {
            try {
                openModuleWindow(buttonText);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private void openModuleWindow(String moduleName) throws Exception {
        switch (moduleName) {
            case "Room Reservations":
                openWindow(new RoomReservationsUI(), "Room Reservations");
                break;
            case "Check-In/Check-Out":
                openWindow(new CheckInOutUI(), "Check-In/Check-Out");
                break;
            case "Billing and Invoicing":
                openWindow(new BillingUI(), "Billing and Invoicing");
                break;
            case "Room Management":
                openWindow(new RoomManagementUI(), "Room Management");
                break;
        }
    }

    private void openWindow(Application module, String title) throws Exception {
        Stage moduleStage = new Stage();
        moduleStage.setTitle(title);
        module.start(moduleStage);
    }
}
