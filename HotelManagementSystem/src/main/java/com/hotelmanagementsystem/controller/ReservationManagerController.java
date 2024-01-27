package com.hotelmanagementsystem.controller;

import com.hotelmanagementsystem.dal.ReservationQueries;
import com.hotelmanagementsystem.object.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Date;
import java.util.List;

public class ReservationManagerController {

        @FXML
        private TableColumn<Reservation, String> contactC;

        @FXML
        private MenuItem contextDelete;

        @FXML
        private ContextMenu contextMenu;

        @FXML
        private Button createReservation;

        @FXML
        private MenuItem editReservation;

        @FXML
        private TableColumn<Reservation, Date> endDateC;

        @FXML
        private TableColumn<Reservation, String> guestNameC;

        @FXML
        private HBox hbox;

        @FXML
        private Label label;

        @FXML
        private TableColumn<Reservation, String> receptionistC;

        @FXML
        private Button refreshButton;

        @FXML
        private TableColumn<Reservation, String> roomNumberC;

        @FXML
        private Pane root;

        @FXML
        private TextField searchBar;

        @FXML
        private TableColumn<Reservation, Date> startDateC;

        @FXML
        private TableView<Reservation> tableView;

        @FXML
        private TableColumn<Reservation, String> totalPriceC;

        @FXML
        void deleteAndRefresh(ActionEvent event) {

        }

        @FXML
        void editReservation(ActionEvent event) {

        }

        @FXML
        void filterByContactNumber(ActionEvent event) {

        }

        @FXML
        void newReservation(ActionEvent event) {

        }

        @FXML
        void refreshTable(ActionEvent event) {
initialize();
        }

        @FXML
        void initialize(){
                contactC.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
                endDateC.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                startDateC.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                guestNameC.setCellValueFactory(new PropertyValueFactory<>("guestName"));
                roomNumberC.setCellValueFactory(new PropertyValueFactory<>("room"));
                totalPriceC.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
                receptionistC.setCellValueFactory(new PropertyValueFactory<>("user"));

                List<Reservation> reservations = ReservationQueries.getAllReservations();
                ObservableList<Reservation> observableReservations = FXCollections.observableArrayList(reservations);
                tableView.setItems(observableReservations);
        }

}
