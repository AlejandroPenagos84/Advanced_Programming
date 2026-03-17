package org.example.controller;

import org.example.model.conecction.PropertiesConnection;

public class FileManager {
    private final PropertiesConnection propertiesConnection;

    public FileManager(String path) {
        this.propertiesConnection = new PropertiesConnection(path);
    }

    public String[] getDatabaseConfig() {
        var properties = propertiesConnection.read();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        return new String[]{url, user, password};
    }
}
