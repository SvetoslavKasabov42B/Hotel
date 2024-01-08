package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import com.example.hotelmanagementsystem.userinterface.screen.AccountManager;
import com.example.hotelmanagementsystem.userinterface.screen.GuestManager;
import com.example.hotelmanagementsystem.userinterface.screen.ReservationManager;
import com.example.hotelmanagementsystem.userinterface.screen.RoomRack;
import javafx.stage.Stage;

public class MenuHandler {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";
    DataAccessLayer dal = new DataAccessLayer(DB_URL,DB_USER,DB_PASSWORD);
    public void openAccountManager() {
        // Create a new instance of the MainMenuApp and call its start method
        AccountManager AccountManager = new AccountManager();
        Stage primaryStage = new Stage();
        AccountManager.start(primaryStage);

    }
    public void openGuestManager(){
        GuestManager guestManager = new GuestManager(dal);
        Stage primaryStage = new Stage();
        guestManager.start(primaryStage);
    }

    public void openReservationManager() {
        ReservationManager rm = new ReservationManager();
        Stage primaryStage = new Stage();
        rm.start(primaryStage);
    }
    public void openRackRoom(){
        RoomRack roomRack = new RoomRack();
        Stage primaryStage = new Stage();
        roomRack.start(primaryStage);
    }
}
