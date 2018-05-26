package com.practice.zookeeper.subscribe;

import java.io.Serializable;

public class ServerData implements Serializable {
    private static final long serialVersionUID = 7115995921706398387L;

    private String address;

    private String id;

    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerData{" +
                "address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}