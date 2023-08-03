module com.mcachedb.mcachedbconsole {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
//    requires javafx.swing;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolboxfx;
    requires eu.hansolo.toolbox;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;

    opens com.mcachedb.mcachedbconsole to javafx.fxml;
    opens com.mcachedb.mcachedbconsole.Request to com.google.gson;
//    opens com.mcachedb.mcachedbconsole.Request to javafx.base;
    exports com.mcachedb.mcachedbconsole;
    exports com.mcachedb.mcachedbconsole.Request;
}