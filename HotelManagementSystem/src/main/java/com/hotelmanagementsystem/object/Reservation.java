package com.hotelmanagementsystem.object;


import java.util.Date;

public class Reservation {
    private String user;
    private String room;
    private Date startDate;
    private Date endDate;
    private String guestName;
    private String contactNumber;
    private double totalPrice;

    public Reservation(String contactNumber, String user, String room, Date startDate, Date endDate,
                       String guestName, double totalPrice) {
        this.contactNumber = contactNumber;
        this.user = user;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestName = guestName;
        this.totalPrice = totalPrice;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
