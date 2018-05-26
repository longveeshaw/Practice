package com.practice.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class NodeExistAsyncDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState().equals(Event.KeeperState.SyncConnected)) {
                        String path = event.getPath();
                        if (event.getType().equals(Event.EventType.None) && path == null) {
                            zooKeeper.exists("/znode_50000000011", true, new AsyncCallback.StatCallback() {
                                public void processResult(int rc, String path, Object ctx, Stat stat) {
                                    System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], stat = [" + stat + "]");
                                }
                            }, "test ctx");
                        }
                    }
                }
            });
            Thread.sleep(Integer.MAX_VALUE);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
