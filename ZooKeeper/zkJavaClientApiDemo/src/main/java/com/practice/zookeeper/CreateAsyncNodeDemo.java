package com.practice.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateAsyncNodeDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (state.equals(Event.KeeperState.SyncConnected)) {
                    System.out.println("state = " + state);
                    zooKeeper.create("/asycNode", "111".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
                        public void processResult(int rc, String path, Object ctx, String name) {
                            System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], name = [" + name + "]");
                        }
                    }, "hello");

                }

            }
        });
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("state = " + state);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
