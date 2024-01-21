package com.hotelmanagementsystem.dal;

public class Connection {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public Connection(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;

    }

}