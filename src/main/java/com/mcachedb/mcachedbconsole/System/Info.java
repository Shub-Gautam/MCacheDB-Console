package com.mcachedb.mcachedbconsole.System;

public class Info {

    private static Info data = new Info(8080);
    int port ;
    int selectedDB ;

    public Info(int port) {
        this.port = port;
    }
//
//    public Info(int port, int selectedDB) {
//        this.port = port;
//        this.selectedDB = selectedDB;
//    }

    public static Info Info(){
        return data ;
    }

    public int getPort() {
        return data.port;
    }

    public void setPort(int port) {
        this.data.port = port;
    }

    public int getSelectedDB() {
        return data.selectedDB;
    }

    public void setSelectedDB(int selectedDB) {
        this.data.selectedDB = selectedDB;
    }
}
