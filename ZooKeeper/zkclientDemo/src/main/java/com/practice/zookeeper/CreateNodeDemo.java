package com.practice.zookeeper;

import com.practice.zookeeper.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

public class CreateNodeDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());
        User user = new User();
        user.setAge(17);
        user.setUsername("Amy");
        String path = zkClient.create("/zkCliNode3", user, CreateMode.PERSISTENT);
        System.out.println("path = " + path);
    }
}
