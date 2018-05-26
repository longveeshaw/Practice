package com.practice.zookeeper;

import com.practice.zookeeper.model.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class GetNodeDataDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());
        Stat stat=new Stat();
        User user= zkClient.readData("/zkCliNode3", stat);
        System.out.println("stat = " + stat);
        System.out.println("user = " + user);
    }
}
