package com.practice.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class DelNodeDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());
        boolean delete = zkClient.delete("/node");
        System.out.println("delete = " + delete);
        boolean b = zkClient.deleteRecursive("/parentNode");
        System.out.println("b = " + b);
    }
}
