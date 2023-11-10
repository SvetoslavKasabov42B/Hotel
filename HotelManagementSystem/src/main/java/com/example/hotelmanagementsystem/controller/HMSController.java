package com.example.hotelmanagementsystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HMSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}