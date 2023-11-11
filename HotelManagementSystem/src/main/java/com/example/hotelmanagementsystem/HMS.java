package com.example.hotelmanagementsystem;


import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class HMS extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HMS.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        final String dbname="postgres";
        final String user="postgres";
        final String pass = "1234";

        DataAccessLayer db = new DataAccessLayer();
        db.connection(dbname,user,pass);
        launch();
    }
}