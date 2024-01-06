package com.example.hotelmanagementsystem.userinterface.screen;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AccountManager extends Application {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    private DataAccessLayer dal;

    private TextField usernameField;
    private PasswordField passwordField;
    private ChoiceBox<String> accountTypeChoiceBox;
    private ListView<String> userList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Account Manager");

        // Initialize the DAL
        dal = new DataAccessLayer(DB_URL, DB_USER, DB_PASSWORD);

        // Create UI components
        Label titleLabel = new Label("Account Manager");
        titleLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();

        Label accountTypeLabel = new Label("Account Type:");
        accountTypeChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Admin", "Receptionist"));
        accountTypeChoiceBox.setValue("Admin");

        Button createUserButton = new Button("Create User");
        createUserButton.setOnAction(e -> createUser());

        Button deleteUserButton = new Button("Delete User");
        deleteUserButton.setOnAction(e -> deleteUser());

        userList = new ListView<>();
        updateUsersList();

        // Create VBox layout
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().addAll(
                titleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                accountTypeLabel, accountTypeChoiceBox,
                createUserButton,
                deleteUserButton,
                new Label("Existing Users:"),
                userList
        );

        // Create the scene
        Scene scene = new Scene(vBox, 400, 400);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void createUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String accountType = accountTypeChoiceBox.getValue();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Input Error", "Username and Password are required", "Please enter both username and password.");
            return;
        }

        if (dal.createUser(username, password, accountType)) {
            showAlert("Success", "User created successfully", "New user has been added to the database.");
            updateUsersList();
        } else {
            showAlert("Error", "Failed to create user", "An error occurred while creating the user.");
        }
    }
    private void deleteUser() {
        String selectedUser = userList.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            String[] userParts = selectedUser.split(" ");
            String usernameToDelete = userParts[0];
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete the user?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.showAndWait();

            if (confirmation.getResult() == ButtonType.YES) {
                if (dal.deleteUser(usernameToDelete)) {
                    showAlert("Success", "User deleted successfully", "The selected user has been deleted.");
                    updateUsersList();
                } else {
                    showAlert("Error", "Failed to delete user", "An error occurred while deleting the user.");
                }
            }
        } else {
            showAlert("No User Selected", "Please select a user to delete", "Select a user from the list before pressing delete.");
        }
    }

    private void updateUsersList() {
        ObservableList<String> users = FXCollections.observableArrayList(dal.getAllUsers());
        userList.setItems(users);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
