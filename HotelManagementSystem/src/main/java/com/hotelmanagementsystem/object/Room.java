package com.hotelmanagementsystem.object;

import com.hotelmanagementsystem.enums.RoomStatus;
import com.hotelmanagementsystem.enums.RoomType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final StringProperty roomNumber;
    private final StringProperty roomType;
    private final StringProperty status;
    private final StringProperty roomPrice;
    public Room(String roomNumber, RoomType roomType, RoomStatus status, Double roomPrice) {
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType.toString());
        this.status = new SimpleStringProperty(status.toString());
        this.roomPrice = new SimpleStringProperty(roomPrice.toString());
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public StringProperty roomNumberProperty() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getRoomPrice() {
        return roomPrice.get();
    }

    public StringProperty roomPriceProperty() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice.set(roomPrice);
    }

    // Getters and setters for roomType
    public String getRoomType() {
        return roomType.get();
    }

    public StringProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType.set(roomType.toString());
    }

    // Getters and setters for status
    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status.set(status.toString());
    }
}
