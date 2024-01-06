package com.example.hotelmanagementsystem.misc;

import java.util.Date;

public class Reservation {
    private String pin;
    private int reservationId;
    private String firstName;
    private String lastName;
    private int roomNumber;
    private Date checkinDate;
    private Date checkoutDate;

    public Reservation(int reservationId, String firstName, String lastName, int roomNumber, Date checkinDate, Date checkoutDate, String pin) {
        this.reservationId = reservationId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.pin = pin;
    }

    // Getters and setters for each field

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }


    public java.sql.Date getCheckInDate() {
        return (java.sql.Date) this.checkinDate;
    }

    public java.sql.Date getCheckOutDate() {
        return (java.sql.Date) this.checkoutDate;
    }

    public String getGuestId() {
        return pin;
    }
}

