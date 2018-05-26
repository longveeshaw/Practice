package com.practice.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AclDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("192.168.129.133:2181", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (state.equals(Event.KeeperState.SyncConnected)) {
                    System.out.println("state = " + state);
                    try {
                        ACL aclIp = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("ip", "127.0.0.1"));
                        ACL aclDigest = new ACL(ZooDefs.Perms.DELETE | ZooDefs.Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("jike:123")));

                        ArrayList<ACL> aclList = new ArrayList<ACL>(16);
                        aclList.add(aclIp);
                        aclList.add(aclDigest);

                        zooKeeper.create("/alcNode", "111".getBytes(), aclList, CreateMode.PERSISTENT_SEQUENTIAL, new AsyncCallback.StringCallback() {
                            public void processResult(int rc, String path, Object ctx, String name) {
                                System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], name = [" + name + "]");
                            }
                        }, "hello");

                        zooKeeper.addAuthInfo("digest", "jike:1234".getBytes());
                        zooKeeper.create("/alcNode", "111".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT_SEQUENTIAL, new AsyncCallback.StringCallback() {
                            public void processResult(int rc, String path, Object ctx, String name) {
                                System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], name = [" + name + "]");
                            }
                        }, "hello");


                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("state = " + state);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
