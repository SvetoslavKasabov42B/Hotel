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

    opens com.hotelmanagementsystem.object to javafx.base;


    opens com.hotelmanagementsystem.controller to javafx.fxml, javafx.graphics;
    exports com.hotelmanagementsystem.userinterface;
}