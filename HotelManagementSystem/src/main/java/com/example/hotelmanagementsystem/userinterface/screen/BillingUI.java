package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillingUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Billing");

        Button generateBillButton = new Button("Generate Bill");
        generateBillButton.setOnAction(e -> {
            // Handle billing logic here
            System.out.println("Bill generated!");
        });

        VBox vBox = new VBox(generateBillButton);
        Scene scene = new Scene(vBox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
