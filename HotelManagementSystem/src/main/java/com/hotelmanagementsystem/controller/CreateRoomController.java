package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.dal.RoomQueries;
import com.hotelmanagementsystem.dal.UserQueries;
import com.hotelmanagementsystem.enums.RoomStatus;
import com.hotelmanagementsystem.enums.RoomType;
import com.hotelmanagementsystem.object.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CreateRoomController {
    @FXML
    private HBox hbox1;

    @FXML
    private Label confirmationLabel;


    @FXML
    private Button createRoomButton;

    @FXML
    private HBox hbox;

    @FXML
    private TextField roomNumberTextField;

    @FXML
    private Pane root;

    @FXML
    private ChoiceBox<RoomType> typeDropDown;

    @FXML
    void addRoom(ActionEvent event) {
        String number = roomNumberTextField.getText();

        RoomType selectedType = typeDropDown.getValue();

        RoomStatus status = RoomStatus.UNOCCUPIED;

        double price = 0;
        switch (selectedType) {
            case SINGLE -> price=50;
            case DOUBLE -> price = 80;
            case TRIPLE -> price =110;
            case PENTHOUSE -> price = 200;

        }

        Room room = new Room(number, selectedType, status, price);

        if(RoomQueries.insertRoom(room)){
            System.out.println("\nNew room Created!");
            confirmationLabel.setText("New room Created!");
        }else{
            System.out.println("\nRoom with that number already exists");
            confirmationLabel.setText("Room number error");
        }
    }

    @FXML
    void initialize() {
        ObservableList<RoomType> type = FXCollections.observableArrayList(RoomType.values());

        typeDropDown.setItems(type);

        typeDropDown.setValue(RoomType.SINGLE);


    }

}

