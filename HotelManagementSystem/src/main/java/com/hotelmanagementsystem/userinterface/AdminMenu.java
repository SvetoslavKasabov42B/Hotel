package com.hotelmanagementsystem.userinterface;

import com.hotelmanagementsystem.controller.AdminMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMenu extends Application {

    public void startMenu(Stage stage, String username) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotelmanagementsystem/views/MainMenuScreen.fxml"));
        Parent root = loader.load();

        AdminMenuController adminMenuController = loader.getController();
        adminMenuController.setLoggedInUsername(username);
        adminMenuController.setTodayDate();

        stage.setTitle("MainMenu");

        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}

