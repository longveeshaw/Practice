package com.practice.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class SubscribeDataChangeDemo {

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = new ZkClient("127.0.0.1", 5000, 5000, new BytesPushThroughSerializer());

        zkClient.subscribeDataChanges("/subscribedNode", new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("s = [" + s + "], o = [" + o + "]");
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("s = [" + s + "]");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);

    }
}
