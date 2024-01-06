package com.example.hotelmanagementsystem.misc;


import java.util.Date;

public class RoomReservation extends Room {

    private String firstName;
    private String lastName;
    private int reservationId;
    private Date checkinDate;
    private Date checkoutDate;

    public RoomReservation(Room room, int reservationId, String firstName, String lastName, Date checkinDate, Date checkoutDate) {
        super(room.getRoomNumber(), room.getRoomType());
        this.firstName = firstName;
        this.lastName = lastName;
        this.reservationId = reservationId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    public RoomReservation(Room room) {
        super(room.getRoomNumber(), room.getRoomType());
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
}