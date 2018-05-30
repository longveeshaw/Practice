package com.practice.zookeeper.balance.server;

import java.io.Serializable;

public class ServerNodeData implements Serializable,Comparable<ServerNodeData>{

    private static final long serialVersionUID = 4830535187120504435L;

    private String host;

    private int port;

    private int balance;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ServerNodeData{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", balance=" + balance +
                '}';
    }

    public int compareTo(ServerNodeData o) {
        return new Integer(balance).compareTo(o.getBalance());
    }

    public String getAddress() {
        return this.host + ":" + this.port;
    }

}
