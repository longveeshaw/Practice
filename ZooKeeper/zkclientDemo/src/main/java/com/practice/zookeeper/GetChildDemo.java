package com.practice.zookeeper;

import com.practice.zookeeper.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class GetChildDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());
        List<String> children = zkClient.getChildren("/");
        System.out.println("children = " + children);
    }
}
