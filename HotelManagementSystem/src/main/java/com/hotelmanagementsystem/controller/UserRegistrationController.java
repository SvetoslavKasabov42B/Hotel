package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.dal.UserQueries;
import com.hotelmanagementsystem.object.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class UserRegistrationController {

    @FXML
    private Button button;

    @FXML
    private CheckBox checkBox1;

    @FXML
    private HBox hbox;

    @FXML
    private TextField passwordField;

    @FXML
    private Pane root;

    @FXML
    private TextField usernameField;

    @FXML
    private Label confirmationLabel;

    @FXML
    void checked(ActionEvent event) {

    }

    @FXML
    void onClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Determine the role based on the checkbox state
        String role = checkBox1.isSelected() ? "admin" : "receptionist";

        // Create a User object
        User newUser = new User(username, password, role);

        // Insert the user into the database
        if(UserQueries.insertUser(newUser)){
            System.out.println("\nNew user registered!");
            confirmationLabel.setText("New user registered!");
        }else{
            System.out.println("\nUsername already exists");
            confirmationLabel.setText("Username already exists");
        }
    }

}
