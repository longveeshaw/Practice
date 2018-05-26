package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AclDemo {
    public static void main(String[] args) throws Exception {


        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .authorization("digest", "lbd:1234".getBytes()).build();
        client.start();

        ACL ipAlc = new ACL(ZooDefs.Perms.ALL, new Id("ip", "192.168.1.1"));
        ACL digestAlc = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,
                new Id("digest", DigestAuthenticationProvider.generateDigest("lbd:1234")));
        ArrayList<ACL> acls = new ArrayList<ACL>(2);
        acls.add(ipAlc);
        acls.add(digestAlc);
        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(acls).forPath("/curatorAclNode","123".getBytes());

        System.out.println("path = " + path);


        //========= read data ===========

        byte[] bytes = client.getData().forPath("/curatorAclNode");
        System.out.println("new String(bytes) = " + new String(bytes));

    }
}
