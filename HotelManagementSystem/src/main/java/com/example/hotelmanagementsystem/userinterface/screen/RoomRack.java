package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import com.example.hotelmanagementsystem.misc.Room;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class RoomRack extends Application {

    private DataAccessLayer dal;

    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private Button refreshButton;
    private TilePane roomRack; // Declare roomRack at the class level

    @Override
    public void start(Stage primaryStage) {
        dal = new DataAccessLayer("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");

        primaryStage.setTitle("Room Rack");

        // Top part: Date Pickers and Refresh Button
        GridPane topGrid = new GridPane();
        topGrid.setHgap(10);
        topGrid.setVgap(10);
        topGrid.setPadding(new Insets(20, 20, 20, 20));

        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        refreshButton = new Button("Refresh");

        topGrid.add(startDatePicker, 0, 0);
        topGrid.add(endDatePicker, 1, 0);
        topGrid.add(refreshButton, 2, 0);

        // Center part: Room Rack (Tile-based grid)
        roomRack = new TilePane(); // Initialize roomRack
        roomRack.setHgap(10);
        roomRack.setVgap(10);
        roomRack.setPadding(new Insets(20, 20, 20, 20));
        roomRack.setAlignment(Pos.CENTER);

        // Populate and update the room rack
        List<Room> rooms = dal.getAllRooms();
        dal.updateRoomStatusToday(rooms);
        displayRoomRack(rooms);

        // Event handling for the Refresh button
        refreshButton.setOnAction(e -> updateRoomRack());

        // Main layout
        GridPane mainLayout = new GridPane();
        mainLayout.setHgap(10);
        mainLayout.setVgap(10);
        mainLayout.setPadding(new Insets(20, 20, 20, 20));

        mainLayout.add(topGrid, 0, 0);
        mainLayout.add(roomRack, 0, 1);

        Scene scene = new Scene(mainLayout, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateRoomRack() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        List<Room> rooms = dal.getAllRooms();
        if (startDate != null && endDate != null ){
            if(!endDate.isBefore(startDate) || startDate.isEqual(endDate)) {
                dal.updateRoomStatus(rooms, startDate, endDate);
            } else {
                displayChekoutBeforeCheckin();
            }
        }else{
            dal.updateRoomStatusToday(rooms);
        }
        displayRoomRack(rooms);
    }

    private void displayChekoutBeforeCheckin() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Date out of range");
        alert.setHeaderText(null);
        alert.setContentText("Check-in date cant be bigger than check-out date.");
        alert.showAndWait();
    }

    private void displayRoomRack(List<Room> rooms) {
        // Clear existing room rack
        roomRack.getChildren().clear();

        // Populate room rack with tiles
        for (Room room : rooms) {
            Button roomTile = new Button(String.valueOf(room.getRoomNumber()));
            roomTile.setPrefSize(80, 80);

            if (room.isOccupied()) {
                roomTile.setStyle("-fx-background-color: red;");
            } else {
                roomTile.setStyle("-fx-background-color: green;");
            }

            roomRack.getChildren().add(roomTile);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
