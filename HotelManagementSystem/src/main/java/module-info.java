module com.example.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.example.hotelmanagementsystem to javafx.fxml;
    exports com.example.hotelmanagementsystem;
    exports com.example.hotelmanagementsystem.controller;
    opens com.example.hotelmanagementsystem.controller to javafx.fxml;
}