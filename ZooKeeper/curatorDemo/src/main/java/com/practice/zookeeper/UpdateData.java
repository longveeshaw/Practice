package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

public class UpdateData {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000).retryPolicy(new RetryNTimes(5, 1000)).build();
        client.start();
        Stat stat=new Stat();
        client.getData().storingStatIn(stat).forPath("/curatorNode");

        int version = stat.getVersion();
        System.out.println("version = " + version);
        Stat stat1 = client.setData().withVersion(version).forPath("/curatorNode", "234".getBytes());
        System.out.println("stat1 = " + stat1);

        int version1 = stat1.getVersion();
        System.out.println("version1 = " + version1);

    }
}
