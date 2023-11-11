package com.example.hotelmanagementsystem;

import com.example.hotelmanagementsystem.dbconnection.DataAccessLayer;
import com.example.hotelmanagementsystem.userinterface.HMS;

public class Application {
    static final String dbname = "postgres";
    static final String user = "postgres";
    static final String pass = "1234";
    public static void main(String[] args) {
        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        dataAccessLayer.connection(dbname,user,pass);
        HMS.main(args);
    }
}
