package com.practice.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;

public class CreateNode {

    public static void main(String[] args) throws Exception {

//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
        RetryPolicy retryPolicy = new RetryNTimes(5, 10000);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1", 5000, 5000, retryPolicy);
        client.start();

        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/curatorNode/1", "123".getBytes());
        System.out.println("path = " + path);


    }
}
