package com.practice.zookeeper.balance.server;

public interface RegisterProvider {

    void register(Object ctx);

    void unRegister(Object ctx);
}
