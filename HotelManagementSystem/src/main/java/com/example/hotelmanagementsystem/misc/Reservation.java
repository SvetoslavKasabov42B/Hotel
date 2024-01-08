package com.example.hotelmanagementsystem.misc;

import eu.hansolo.toolbox.properties.IntegerProperty;
import eu.hansolo.toolbox.properties.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Reservation {

        private int reservationId;
        private String guestId;  // Update to guestId
        private int roomNumber;
        private Date checkinDate;
        private Date checkoutDate;

        public Reservation(int reservationId, String guestId, int roomNumber, Date checkinDate, Date checkoutDate) {
            this.reservationId = reservationId;
            this.guestId = guestId;
            this.roomNumber = roomNumber;
            this.checkinDate = checkinDate;
            this.checkoutDate = checkoutDate;
        }

        // Getters and setters for each field

        public int getReservationId() {
            return reservationId;
        }

        public String getGuestId() {
            return guestId;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public Date getCheckinDate() {
            return checkinDate;
        }

        public Date getCheckoutDate() {
            return checkoutDate;
        }
    }

