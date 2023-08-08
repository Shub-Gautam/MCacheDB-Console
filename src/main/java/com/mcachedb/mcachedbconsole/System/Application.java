package com.mcachedb.mcachedbconsole.System;

public class Application {
    private Server server;
    public Server getServer() {
        return server;
    }
    public void setServer(Server server) {
        this.server = server;
    }

    public Application(Server server) {
        this.server = server;
    }

    public Application() {
    }
}
