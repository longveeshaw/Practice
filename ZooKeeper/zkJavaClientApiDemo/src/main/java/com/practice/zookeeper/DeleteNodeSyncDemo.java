package com.practice.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class DeleteNodeSyncDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper=new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState().equals(Event.KeeperState.SyncConnected)) {
                        if (event.getType().equals(Event.EventType.None) &&  event.getPath() == null) {
                            try {
                                zooKeeper.delete("/znode_40000000007",-1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (KeeperException e) {
                                e.printStackTrace();
                            }

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
