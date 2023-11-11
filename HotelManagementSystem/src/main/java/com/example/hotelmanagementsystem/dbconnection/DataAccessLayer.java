package com.example.hotelmanagementsystem.dbconnection;

import java.sql.*;

public class DataAccessLayer {
    public Connection connection(String dbname, String user, String pass) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=public"+dbname,user,pass);
       if(connection != null){
       System.out.println("Connection established");
       }else {
           System.out.println("Connection failed");
       }

        }catch (Exception e){
            System.out.println(e);
        }
        return connection;
    }


}
