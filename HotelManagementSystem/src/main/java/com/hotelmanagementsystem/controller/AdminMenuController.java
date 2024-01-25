package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.userinterface.UserRegistrationMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

public class AdminMenuController {

    public HBox hbox1;
    @FXML
    private Button checkIn;

    @FXML
    private Button checkOut;

    @FXML
    private HBox hbox;

    @FXML
    private Label mainMenu;

    @FXML
    private Button monthlyReport;

    @FXML
    private Button rackRoom;

    @FXML
    private Button reservations;

    @FXML
    private Label reservationsCount;

    @FXML
    private Pane root;

    @FXML
    private Label todayDate;

    @FXML
    private Label todayGuests;

    @FXML
    private Button userManagement;

    @FXML
    private Label wellcomeMSG;

    @FXML
    void openCheckInMenu(ActionEvent event) {

    }

    @FXML
    void openCheckOutMenu(ActionEvent event) {

    }

    @FXML
    void openMonthlyReportMenu(ActionEvent event) {

    }

    @FXML
    void openRackRoomMenu(ActionEvent event) {

    }

    @FXML
    void openReservationsMenu(ActionEvent event) {

    }

    @FXML
    void openUserManagementMenu(ActionEvent event) {
        UserRegistrationMenu userRegistrationMenu = new UserRegistrationMenu();
        userRegistrationMenu.showUserRegistrationMenu();
    }
    @FXML
    public void setLoggedInUsername(String username){
        wellcomeMSG.setText("Wellcome, "+username + " !");
    }

    @FXML
    public void setTodayDate() {
        todayDate.setText(String.valueOf(LocalDate.now()));
    }


}
