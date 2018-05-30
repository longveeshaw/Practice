package com.practice.zookeeper;

import com.practice.zookeeper.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

public class WriteDataDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());

        User user = new User();
        user.setAge(18);
        user.setUsername("Caroline");

        zkClient.writeData("/zkCliNode3", user);

        User userNew = zkClient.readData("/zkCliNode3");
        System.out.println("userNew = " + userNew);

    }
}
