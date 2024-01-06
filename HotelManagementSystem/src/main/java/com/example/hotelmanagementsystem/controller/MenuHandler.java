package com.example.hotelmanagementsystem.controller;

import com.example.hotelmanagementsystem.userinterface.screen.AccountManager;
import com.example.hotelmanagementsystem.userinterface.screen.GuestManager;
import javafx.stage.Stage;

public class MenuHandler {
    public void openAccountManager() {
        // Create a new instance of the MainMenuApp and call its start method
        AccountManager AccountManager = new AccountManager();
        Stage primaryStage = new Stage();
        AccountManager.start(primaryStage);

    }
    public void openGuestManager(){
        GuestManager guestManager = new GuestManager();
        Stage primaryStage = new Stage();
        guestManager.start(primaryStage);
    }
}
