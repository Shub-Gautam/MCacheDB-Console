package com.mcachedb.mcachedbconsole.System;

public class Info {

    private static Info data = new Info(8080);
    int port ;
    String selectedDB ;


    String hostAddress ;

    public String getHostAddress() {
        return data.hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        data.hostAddress = hostAddress;
    }


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

    public String getSelectedDB() {
        return data.selectedDB;
    }

    public void setSelectedDB(String selectedDB) {
        this.data.selectedDB = selectedDB;
    }
}
