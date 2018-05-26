package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncDemo {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000).retryPolicy(new RetryNTimes(5, 1000)).build();
        client.start();

        ExecutorService pool = Executors.newFixedThreadPool(5);
        client.checkExists().inBackground(new BackgroundCallback() {
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                Stat stat = curatorEvent.getStat();
                System.out.println("stat = " + stat);
            }
        }, "test context", pool).forPath("/curatorNode");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
