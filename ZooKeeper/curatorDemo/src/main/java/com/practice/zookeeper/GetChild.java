package com.practice.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

public class GetChild {

    public static void main(String[] args) throws Exception {

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1", 5000, 5000, retryPolicy);
        client.start();

        List<String> strings = client.getChildren().forPath("/curatorNode");
        System.out.println("strings = " + strings);


    }
}
