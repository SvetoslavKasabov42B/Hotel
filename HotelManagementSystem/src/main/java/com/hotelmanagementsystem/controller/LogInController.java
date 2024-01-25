package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.dal.UserQueries;
import com.hotelmanagementsystem.userinterface.AdminMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LogInController {
        @FXML
        private Pane root;

        @FXML
        private PasswordField passwordTF;

        @FXML
        private TextField usernameTF;

        @FXML
        void LogInClicked(ActionEvent event) throws Exception {
                String username = usernameTF.getText();
                String enteredPassword = passwordTF.getText();

                if (UserQueries.isValidUser(username, enteredPassword)) {
                        // Valid credentials, login successful
                        System.out.println("Login successful");
                        AdminMenu adminMenu = new AdminMenu();
                        Stage primaryStage = new Stage();
                        adminMenu.startMenu(primaryStage,username);
                } else {
                        // Invalid credentials, show an error message
                        System.out.println("Invalid username or password");
                }

        }

    }

