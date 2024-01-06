package com.example.hotelmanagementsystem.misc;

public class Room {

    private int roomNumber;
    private String roomType;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }


    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
