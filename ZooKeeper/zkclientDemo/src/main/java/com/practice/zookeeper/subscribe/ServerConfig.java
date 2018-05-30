package com.practice.zookeeper.subscribe;

import java.io.Serializable;

public class ServerConfig implements Serializable{

    private static final long serialVersionUID = -8153881899703792518L;
    private String dbUrl;
    private String dbUsername;
    private String dbPsw;

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPsw() {
        return dbPsw;
    }

    public void setDbPsw(String dbPsw) {
        this.dbPsw = dbPsw;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "dbUrl='" + dbUrl + '\'' +
                ", dbUsername='" + dbUsername + '\'' +
                ", dbPsw='" + dbPsw + '\'' +
                '}';
    }
}

