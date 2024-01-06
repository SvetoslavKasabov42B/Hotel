package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import com.example.hotelmanagementsystem.misc.Reservation;

import com.example.hotelmanagementsystem.misc.Room;
import com.example.hotelmanagementsystem.misc.RoomReservation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public class ReservationManager extends Application {

    private DataAccessLayer dal;

    private TextField firstNameField;

    private TextField lastNameField;

    private TextField idNumberField;

    private TextField roomNumberField;

    private TextField roomTypeField;

    private TextField reservationNumberField;

    private DatePicker checkInDateField;

    private DatePicker checkOutDateField;

    private TableView<RoomReservation> table;
    private Button checkAvailabilityBtn;

    @Override
    public void start(Stage primaryStage) {

        dal = new DataAccessLayer("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");

        primaryStage.setTitle("Reservation Manager");

        // Top part: Text fields
        GridPane topGrid = new GridPane();
        topGrid.setHgap(10);
        topGrid.setVgap(10);
        topGrid.setPadding(new Insets(20, 20, 20, 20));


        topGrid.add(new Label("First Name:"), 0, 0);
        firstNameField = new TextField();
        topGrid.add(firstNameField, 1, 0);

        topGrid.add(new Label("Last Name:"), 0, 1);
        lastNameField = new TextField();
        topGrid.add(lastNameField, 1, 1);

        topGrid.add(new Label("ID Number:"), 0, 2);
        idNumberField = new TextField();
        topGrid.add(idNumberField, 1, 2);

        topGrid.add(new Label("Room Number:"), 2, 0);
        roomNumberField = new TextField();
        topGrid.add(roomNumberField, 3, 0);

        topGrid.add(new Label("Room Type:"), 2, 1);
        roomTypeField = new TextField();
        topGrid.add(roomTypeField, 3, 1);

        topGrid.add(new Label("Reservation Number:"), 2, 2);
        reservationNumberField = new TextField();
        topGrid.add(reservationNumberField, 3, 2);

        topGrid.add(new Label("Check-In Date:"), 4, 0);
        checkInDateField = new DatePicker();
        topGrid.add(checkInDateField, 5, 0);

        topGrid.add(new Label("Check-Out Date:"), 4, 1);
        checkOutDateField = new DatePicker();
        topGrid.add(checkOutDateField, 5, 1);

        checkAvailabilityBtn = new Button("Check Availability");

        HBox rd = new HBox(10);

        TableColumn<RoomReservation, Integer> idColumn = new TableColumn<>("Reservation ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId"));

        TableColumn<RoomReservation, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<RoomReservation, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<RoomReservation, Integer> roomNumberColumn = new TableColumn<>("Room Number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<RoomReservation, String> roomTypeColumn = new TableColumn<>("Room Type");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<RoomReservation, Date> checkinDateColumn = new TableColumn<>("Check-In Date");
        checkinDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkinDate"));

        TableColumn<RoomReservation, Date> checkoutDateColumn = new TableColumn<>("Check-Out Date");
        checkoutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));

        // Initialize TableView
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, roomNumberColumn, roomTypeColumn, checkinDateColumn, checkoutDateColumn);

        updateTable();

        table.setPrefWidth(900);
        rd.getChildren().addAll(table);

        // Right part: Buttons
        VBox buttonBox = new VBox(10);
        Button createReservationButton = new Button("Create Reservation");
        Button checkInButton = new Button("Check In");
        Button checkOutButton = new Button("Check Out");

        buttonBox.getChildren().addAll(createReservationButton, checkInButton, checkOutButton, checkAvailabilityBtn);

        // Main layout
        VBox mainLayout = new VBox(20);
        HBox mainTop = new HBox(10);
        HBox mainBottom = new HBox(10);

        mainBottom.setAlignment(Pos.CENTER);
        mainBottom.getChildren().addAll(rd);

        mainTop.getChildren().addAll(topGrid,buttonBox);
        mainLayout.getChildren().addAll(mainTop, mainBottom);

        Scene scene = new Scene(mainLayout, 1200, 600);

        mainLayout.widthProperty().addListener((obs, oldVal, newVal) ->
                rd.setPrefWidth(0.8 * mainLayout.getWidth()));
        mainLayout.widthProperty().addListener((obs, oldVal, newVal) ->
                mainTop.setPrefWidth(0.8 * mainLayout.getWidth()));

        primaryStage.setScene(scene);

        primaryStage.show();

        checkAvailabilityBtn.setOnAction(e -> {

            if (checkInDateField.getValue() != null && checkOutDateField.getValue() != null && !roomNumberField.getText().isEmpty()) {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                LocalDate checkInDate = checkInDateField.getValue();
                LocalDate checkOutDate = checkOutDateField.getValue();
                List<RoomReservation> availableRooms = dal.getAvailableRooms(Date.valueOf(checkOutDate), Date.valueOf(checkInDate),roomNumber);


                if (!availableRooms.isEmpty()){
                    ObservableList<RoomReservation> roomObservableList = FXCollections.observableArrayList(availableRooms);
                    table.setItems(roomObservableList);
                } else {
                    showRoomNotAvailableAlert();
                    table.setItems(null);
                }
            }else{
                showIncompleteInformationAlert();
            }
        });
    }

    private void showIncompleteInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Information");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the required information (Check-In Date, Check-Out Date, and Room Number).");
        alert.showAndWait();
    }

    private boolean areAllFieldsFilled() {
        return !firstNameField.getText().isEmpty()
                && !lastNameField.getText().isEmpty()
                && !idNumberField.getText().isEmpty()
                && !roomNumberField.getText().isEmpty()
                && !roomTypeField.getText().isEmpty()
                && !reservationNumberField.getText().isEmpty()
                && checkInDateField.getValue() != null
                && checkOutDateField.getValue() != null;
    }
    private void showRoomNotAvailableAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Room Not Available");
        alert.setHeaderText(null);
        alert.setContentText("The selected room is not available for the specified time period.");
        alert.showAndWait();
    }

    private void updateTable() {
        List<RoomReservation> reservations = dal.getAllReservations();
        ObservableList<RoomReservation> reservationObservableList = FXCollections.observableArrayList(reservations);
        table.setItems(reservationObservableList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
