package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.controller.MenuHandler;
import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;

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

    private TextField idNumberField;

    private TextField roomNumberField;

    private ComboBox<String> roomTypeComboBox;

    private TextField reservationNumberField;

    private DatePicker checkInDateField;

    private DatePicker checkOutDateField;

    private TableView<RoomReservation> table;
    private Button checkAvailabilityBtn;
    private Button guestButton;
    private Button refresh;

    @Override
    public void start(Stage primaryStage) {

        dal = new DataAccessLayer("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");

        primaryStage.setTitle("Reservation Manager");

        // Top part: Text fields
        GridPane topGrid = new GridPane();
        topGrid.setHgap(10);
        topGrid.setVgap(10);
        topGrid.setPadding(new Insets(20, 20, 20, 20));

        topGrid.add(new Label("ID Number:"), 0, 0);
        idNumberField = new TextField();
        topGrid.add(idNumberField, 1, 0);

        topGrid.add(new Label("Room Number:"), 2, 0);
        roomNumberField = new TextField();
        topGrid.add(roomNumberField, 3, 0);

        topGrid.add(new Label("Room Type:"), 2, 1);
        roomTypeComboBox = new ComboBox<>();
        List<String> roomTypes = dal.getRoomTypes();
        roomTypes.add(0, null);
        roomTypeComboBox.setItems(FXCollections.observableArrayList(roomTypes));
        topGrid.add(roomTypeComboBox, 3, 1);

        topGrid.add(new Label("Reservation Number:"), 2, 2);
        reservationNumberField = new TextField();
        topGrid.add(reservationNumberField, 3, 2);

        topGrid.add(new Label("Check-In Date:"), 4, 0);
        checkInDateField = new DatePicker();
        topGrid.add(checkInDateField, 5, 0);

        topGrid.add(new Label("Check-Out Date:"), 4, 1);
        checkOutDateField = new DatePicker();
        topGrid.add(checkOutDateField, 5, 1);

        guestButton = new Button("Add Guest");
        topGrid.add(guestButton,1, 1);
        checkAvailabilityBtn = new Button("Check Availability");

        refresh = new Button("Refresh");
        topGrid.add(refresh,5, 3);

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

        roomNumberField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                int roomNumber = Integer.parseInt(newText);
                String roomType = dal.getRoomTypeByNumber(roomNumber);
                if (roomType != null) {
                    roomTypeComboBox.setValue(roomType);
                }
            }
        });
        reservationNumberField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                int reservationID = Integer.parseInt(newText);
                updateTable(dal.getReservationsById(reservationID));
            }
        });
        idNumberField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                updateTable(dal.getReservationsByGuestId(newText));
            }
        });
        roomTypeComboBox.valueProperty().addListener((obs, oldType, newType) -> {
                if (roomNumberField.getText().isEmpty() && checkInDateField.getValue() != null && checkOutDateField.getValue() != null) {

                    updateTableBasedOnRoomTypeAndNumber();
                }else if(newType == null){
                    updateTable();
                }else{
                    {updateTable(dal.getReservationsByRoomType(newType));}
                }
        });
        guestButton.setOnAction(e -> openAddGuestWindow());

        refresh.setOnAction(e -> {
            updateTable();
            idNumberField.setText("");
            roomNumberField.setText("");
            reservationNumberField.setText("");
            checkInDateField.setValue(null);
            checkOutDateField.setValue(null);
        });
        checkInDateField.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && checkOutDateField.getValue() != null) {
                updateTableBasedOnRoomTypeAndNumber();
            }
        });

        checkOutDateField.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (checkInDateField.getValue() != null && newDate != null) {
                updateTableBasedOnRoomTypeAndNumber();
            }
        });
        checkAvailabilityBtn.setOnAction(e -> {

            if (checkInDateField.getValue() != null && checkOutDateField.getValue() != null ) {
                LocalDate checkInDate = checkInDateField.getValue();
                LocalDate checkOutDate = checkOutDateField.getValue();
                if(checkOutDate.isAfter(checkInDate)){

                    List<RoomReservation> roomAvailability = null;

                    if(!roomNumberField.getText().isEmpty()){
                        int roomNumber = Integer.parseInt(roomNumberField.getText());
                        roomAvailability = dal.getRoomAvailability(Date.valueOf(checkOutDate), Date.valueOf(checkInDate),roomNumber);

                    }else if (roomTypeComboBox.getValue() !=null){
                        roomAvailability = dal.getRoomsDateType(Date.valueOf(checkOutDate), Date.valueOf(checkInDate), roomTypeComboBox.getValue());
                    }
                    if (!roomAvailability.isEmpty()){
                        ObservableList<RoomReservation> roomObservableList = FXCollections.observableArrayList(roomAvailability);
                        updateTable(roomObservableList);
                    } else {
                        showRoomNotAvailableAlert();
                        table.setItems(null);
                    }
                }else{
                    showInDateBiggerOutDateAlert();
                }
            }else {
                showIncompleteInformationAlert();
            }
        });
    }
    private void updateTableBasedOnRoomTypeAndNumber() {
        LocalDate checkIn = checkInDateField.getValue();
        LocalDate checkOut = checkOutDateField.getValue();

        if (checkInDateField.getValue() != null && checkOutDateField.getValue() != null && checkOut.isAfter(checkIn)) {
            Date checkOutDate = Date.valueOf(checkOutDateField.getValue());
            Date checkInDate = Date.valueOf(checkInDateField.getValue());
            List<RoomReservation> roomReservations;
            if (!roomNumberField.getText().isEmpty()) {
                //search by time period and room number
                roomReservations = dal.getReservationsTimeNumber(checkOutDate, checkInDate, roomNumberField.getText());
            } else if (roomTypeComboBox.getValue() != null) {
                //search by time period and type
                roomReservations = dal.getReservationsTimeType(checkOutDate, checkInDate, roomTypeComboBox.getValue());
            }else{
                //search by time period
                roomReservations = dal.getReservationsTime(checkOutDate, checkInDate);

            }
            updateTable(roomReservations);
        }
    }
    private void openAddGuestWindow() {
        MenuHandler menuHandler = new MenuHandler();
        menuHandler.openGuestManager();
    }

    private void showInDateBiggerOutDateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Date out of range");
        alert.setHeaderText(null);
        alert.setContentText("Check-in date cant be bigger than check-out date.");
        alert.showAndWait();
    }


    private void showIncompleteInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Information");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the required information Check In Date / Check Out Date.");
        alert.showAndWait();
    }

    private boolean areAllFieldsFilled() {
        return !idNumberField.getText().isEmpty()
                && !roomNumberField.getText().isEmpty()
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
    private void updateTable(List<RoomReservation> r) {
        ObservableList<RoomReservation> reservationObservableList = FXCollections.observableArrayList(r);
        table.setItems(reservationObservableList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
