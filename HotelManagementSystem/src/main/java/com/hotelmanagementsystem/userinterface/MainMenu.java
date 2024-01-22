package com.hotelmanagementsystem.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/com/hotelmanagementsystem/views/MainMenuScreen.fxml"));

        stage.setTitle("Login");

        stage.setScene(new Scene(root));

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

