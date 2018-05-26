package com.practice.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class CreateSessionDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 10000, 10000, new SerializableSerializer());
        System.out.println("connected");
        System.out.println("zkClient = " + zkClient);
    }
}
