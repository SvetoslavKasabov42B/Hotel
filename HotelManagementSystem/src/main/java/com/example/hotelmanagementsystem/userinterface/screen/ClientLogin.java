package com.example.hotelmanagementsystem.userinterface.screen;
import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientLogin extends Application {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    private DataAccessLayer dal;
    private Button loginButton; // Declare loginButton as an instance variable

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Client Login");

        // Initialize the DAL
        dal = new DataAccessLayer(DB_URL, DB_USER, DB_PASSWORD);

        // Create the grid pane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Add UI components
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        loginButton = new Button("Login"); // Initialize loginButton

        usernameLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        passwordLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        usernameField.setStyle("-fx-font-size: 14;");
        passwordField.setStyle("-fx-font-size: 14;");
        loginButton.setStyle(
                "-fx-font-size: 14; " +
                        "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white;");

        // Add components to the grid
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 1, 2);

        // Set action for the login button
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));

        // Create the scene
        Scene scene = new Scene(grid, 400, 300);

        // Set the scene to the stage
        primaryStage.setScene(scene);
        // Show the stage
        primaryStage.show();
    }

    private void handleLogin(String username, String password) {
        // Check for an existing account using the DAL
        if (dal.checkAccount(username, password)) {
            String accountType = dal.getAccountType(username, password);
            // Account exists, navigate to the main menu
            System.out.println("Login successful!");

            // Close the login stage
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

            if ("Admin".equals(accountType)) {
                openAdminMenuInSameStage();
            } else if ("Receptionist".equals(accountType)) {
                openMainMenuInSameStage();
            } else {
                System.out.println("Unknown account type");
            }

            // Add code to open the main menu or perform other actions
            // For example, you can open the main menu in the same stage as follows:

        } else {
            // Account doesn't exist, show an error message
            showAlert("Login Error", "Account does not exist", "Account username or password is incorrect.");
        }
    }

    private void openMainMenuInSameStage() {
        // Create a new instance of the MainMenuApp and call its start method
        MainMenu mainMenuApp = new MainMenu();
        Stage primaryStage = new Stage();
        mainMenuApp.start(primaryStage);
    }

    private void openAdminMenuInSameStage(){
        //Create a new instance of the AdminMenu and call its start method
        AdminMenu adminMenu = new AdminMenu();
        Stage primaryStage=new Stage();
        adminMenu.start(primaryStage);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


