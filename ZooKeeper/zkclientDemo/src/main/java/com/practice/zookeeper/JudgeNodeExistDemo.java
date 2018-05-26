package com.practice.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class JudgeNodeExistDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());
        boolean exists = zkClient.exists("/zookeeper");
        System.out.println("exists = " + exists);
    }
}
