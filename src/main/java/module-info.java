module com.mcachedb.mcachedbconsole {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
//    requires javafx.swing;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.net.http;

    opens com.mcachedb.mcachedbconsole to javafx.fxml;
    exports com.mcachedb.mcachedbconsole;
}