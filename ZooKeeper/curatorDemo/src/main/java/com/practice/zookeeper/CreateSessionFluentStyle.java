package com.practice.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;

public class CreateSessionFluentStyle {

    public static void main(String[] args) {

//        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);
//        RetryPolicy retryPolicy = new RetryNTimes(5, 10000);
        RetryPolicy retryPolicy = new RetryUntilElapsed(5000, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();


    }
}
