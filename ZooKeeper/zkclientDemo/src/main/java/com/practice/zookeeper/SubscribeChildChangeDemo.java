package com.practice.zookeeper;

import com.practice.zookeeper.model.User;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class SubscribeChildChangeDemo {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new SerializableSerializer());

        zkClient.subscribeChildChanges("/subscribedNode", new IZkChildListener() {
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("s = [" + s + "], list = [" + list + "]");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);

    }
}
