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

    exports com.example.hotelmanagementsystem.controller;
    opens com.example.hotelmanagementsystem.controller to javafx.fxml;
    opens com.example.hotelmanagementsystem.userinterface.screen to javafx.graphics;
}