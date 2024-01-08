package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GuestManager extends Application {

    private DataAccessLayer dal;

    private TextField pinField;
    private TextField phoneNumberField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private ChoiceBox<String> genderChoiceBox;

    private DatePicker dobDataPicker;

    private ListView<String> guestList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Guest Manager");

        // Initialize the DAL
        dal = new DataAccessLayer("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");

        // Create UI components
        Label titleLabel = new Label("Guest Manager");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        guestList = new ListView<>();
        updateGuestList();

        Label insertLabel = new Label("Insert New Guest");
        insertLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        pinField = new TextField();
        pinField.setPromptText("PIN (max 10 digits)");

        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Phone Number (max 13 characters)");

        Label plusLabel = new Label("+");
        plusLabel.setStyle("-fx-background-color: #519; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 4px;");

        // Create an HBox to hold the cube and the phone number field
        HBox phoneBox = new HBox(5, plusLabel, phoneNumberField);
        phoneBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        genderChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("male", "female"));
        genderChoiceBox.setValue("male");

        dobDataPicker = new DatePicker();
        dobDataPicker.setPromptText("Date of Birth");

        emailField = new TextField();
        emailField.setPromptText("email");

        Button insertButton = new Button("Insert Guest");
        insertButton.setOnAction(e -> insertGuest());

        Button deleteButton = new Button("Delete Guest");
        deleteButton.setOnAction(e -> deleteGuest());

        // Create VBox layout
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(
                titleLabel,
                new Label("Guest List:"),
                guestList,
                insertLabel,
                pinField,
                phoneBox,
                firstNameField,
                lastNameField,
                genderChoiceBox,
                dobDataPicker,
                emailField,
                insertButton,
                deleteButton
        );

        // Create the scene
        Scene scene = new Scene(vBox, 400, 500);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void updateGuestList() {
        ObservableList<String> guests = FXCollections.observableArrayList(dal.getAllGuests());
        guestList.setItems(guests);
    }

    private void insertGuest() {
        String pin = pinField.getText();
        String phoneNumber = phoneNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = genderChoiceBox.getValue();
        LocalDate dob = dobDataPicker.getValue();
        String email = emailField.getText();

        if (dob == null) {
            showAlert("Input Error", "Date of Birth is required", "Please select a date of birth.");
            return;
        }

        if (dal.insertGuest(pin, firstName, lastName, phoneNumber, gender, email, dob)) {
            showAlert("Success", "Guest inserted successfully", "New guest has been added to the database.");
            updateGuestList();
        } else {
            showAlert("Error", "Failed to insert guest", "An error occurred while inserting the guest.");
        }

        if (pin.isEmpty() || phoneNumber.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Input Error", "All fields are required", "Please fill in all the guest details.");
            return;
        }

        if (!pin.matches("\\d{1,10}")) {
            showAlert("Invalid PIN", "PIN must be a maximum of 10 digits", "Please enter a valid PIN.");
            return;
        }

        if (!phoneNumber.matches(".{1,13}")) {
            showAlert("Invalid Phone Number", "Phone Number must be a maximum of 13 characters", "Please enter a valid phone number.");
            return;
        }

        if (dal.insertGuest(pin, firstName, lastName, phoneNumber, gender, email, dob)) {
            showAlert("Success", "Guest inserted successfully", "New guest has been added to the database.");
            updateGuestList();
        } else {
            showAlert("Error", "Failed to insert guest", "An error occurred while inserting the guest.");
        }
    }

    private void deleteGuest() {
        String selectedGuest = guestList.getSelectionModel().getSelectedItem();

        if (selectedGuest != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the guest?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.showAndWait();

            if (confirmation.getResult() == ButtonType.YES) {
                // Extract the pin from the selected guest string (assuming the pin is the first part)
                String[] parts = selectedGuest.split(" ");
                String pin = parts[0];

                if (dal.deleteGuest(pin)) {
                    showAlert("Success", "Guest deleted successfully", "The selected guest has been deleted.");
                    updateGuestList();
                } else {
                    showAlert("Error", "Failed to delete guest", "An error occurred while deleting the guest.");
                }
            }
        } else {
            showAlert("No Guest Selected", "Please select a guest to delete", "Select a guest from the list before pressing delete.");
        }
    }


    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
