package com.example.hotelmanagementsystem.userinterface.screen;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BillingUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Billing and Invoicing");

        // Sample data for itemized bills
        ObservableList<String> itemizedBillOptions = FXCollections.observableArrayList(
                "Room Charges", "Food and Beverage", "Other Services"
        );

        // Sample data for payment options
        ObservableList<String> paymentOptions = FXCollections.observableArrayList(
                "Cash", "Credit Card", "Traveler's Checks"
        );

        // UI components
        ListView<String> itemizedBillListView = new ListView<>(itemizedBillOptions);
        Button generateBillButton = new Button("Generate Bill");
        ComboBox<String> paymentOptionsComboBox = new ComboBox<>(paymentOptions);
        Button printInvoiceButton = new Button("Print Invoice");
        Button reconcilePaymentButton = new Button("Reconcile Payment");

        // GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the grid
        gridPane.add(new Label("Itemized Bill:"), 0, 0);
        gridPane.add(itemizedBillListView, 1, 0);
        gridPane.add(generateBillButton, 0, 1);
        gridPane.add(new Label("Payment Options:"), 0, 2);
        gridPane.add(paymentOptionsComboBox, 1, 2);
        gridPane.add(printInvoiceButton, 0, 3);
        gridPane.add(reconcilePaymentButton, 0, 4);

        // Event handling
        generateBillButton.setOnAction(e -> {
            // Implement itemized bill generation logic
            // For simplicity, just display a message
            displayInformationAlert("Generate Bill", "Bill generated for " +
                    itemizedBillListView.getSelectionModel().getSelectedItem());
        });

        printInvoiceButton.setOnAction(e -> {
            // Implement invoice printing logic
            // For simplicity, just display a message
            displayInformationAlert("Print Invoice", "Invoice printed.");
        });

        reconcilePaymentButton.setOnAction(e -> {
            // Implement payment reconciliation logic
            // For simplicity, just display a message
            displayInformationAlert("Reconcile Payment", "Payment reconciled using " +
                    paymentOptionsComboBox.getValue());
        });

        // Scene
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void displayInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

