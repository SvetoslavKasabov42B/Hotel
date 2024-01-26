package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.object.Room;
import com.hotelmanagementsystem.dal.RoomQueries;
import com.hotelmanagementsystem.userinterface.CreateRoomMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RoomManagerController {

    @FXML
    private TableColumn<Room, String> RoomNumberC;

    @FXML
    private TableColumn<Room, String> RoomStatusC;

    @FXML
    private TableColumn<Room, String> RoomTypeC;

    @FXML
    private TableColumn<Room, String> priceTableC;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private HBox hbox;

    @FXML
    private Button refreshButton;

    @FXML
    private TextField roomNumber;

    @FXML
    private Pane root;

    @FXML
    private TableView<Room> tableView;

    @FXML
    void deleteRoom(ActionEvent event) {
       String rNumber = roomNumber.getText();
       RoomQueries.deleteRoomByNumber(rNumber);
       initialize();
    }

    @FXML
    void openRoomCreator(ActionEvent event) {
        CreateRoomMenu createRoomMenu = new CreateRoomMenu();
        createRoomMenu.showCreateRoomMenu();
    }

    @FXML
    void refreshTable(ActionEvent event) {
initialize();
    }

    @FXML
    void initialize() {
        // Initialize TableView columns with Room properties
        RoomNumberC.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty());
        RoomStatusC.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        RoomTypeC.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());
        priceTableC.setCellValueFactory(cellData->cellData.getValue().roomPriceProperty());

        // Populate TableView with data from the database
        ObservableList<Room> rooms = FXCollections.observableArrayList(RoomQueries.getAllRooms());
        tableView.setItems(rooms);
    }
}

