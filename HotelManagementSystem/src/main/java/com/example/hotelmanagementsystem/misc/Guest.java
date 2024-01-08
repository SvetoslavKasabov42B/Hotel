package com.example.hotelmanagementsystem.misc;


public class Guest {
    private String pin;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private String email;

    public Guest(String pin, String firstName, String lastName, String phoneNumber, String gender, String email) {
        this.pin = pin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }
}
