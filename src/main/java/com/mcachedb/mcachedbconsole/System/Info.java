package com.mcachedb.mcachedbconsole.System;

public class Info {
    int port ;
    int selectedDB ;

    public Info(int port) {
        this.port = port;
    }

    public Info(int port, int selectedDB) {
        this.port = port;
        this.selectedDB = selectedDB;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSelectedDB() {
        return selectedDB;
    }

    public void setSelectedDB(int selectedDB) {
        this.selectedDB = selectedDB;
    }
}
