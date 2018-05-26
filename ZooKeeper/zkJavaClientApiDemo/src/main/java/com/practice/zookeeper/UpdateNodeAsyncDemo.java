package com.practice.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class UpdateNodeAsyncDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) throws InterruptedException {

        try {
            zooKeeper=new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState().equals(Event.KeeperState.SyncConnected)) {
                        if (event.getType().equals(Event.EventType.None) && event.getPath() == null) {
                            zooKeeper.setData("/syncPerNode", "456".getBytes(), -1, new AsyncCallback.StatCallback() {
                                public void processResult(int rc, String path, Object ctx, Stat stat) {
                                    System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], stat = [" + stat + "]");
                                }
                            }, "test ctx");
                        }
                    }

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread.sleep(Integer.MAX_VALUE);
    }
}
