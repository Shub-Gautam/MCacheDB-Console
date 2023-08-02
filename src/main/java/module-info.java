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

    opens com.mcachedb.mcachedbconsole to javafx.fxml;
    exports com.mcachedb.mcachedbconsole;
}