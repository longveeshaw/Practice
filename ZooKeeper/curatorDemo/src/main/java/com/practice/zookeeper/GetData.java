package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

public class GetData {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000).retryPolicy(new RetryNTimes(5, 1000)).build();
        client.start();
        Stat stat=new Stat();

        byte[] bytes = client.getData().storingStatIn(stat).forPath("/curatorNode");
        System.out.println("new String(bytes) = " + new String(bytes));
        System.out.println("stat = " + stat);
    }
}
