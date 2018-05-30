package com.practice.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class NodeExistSyncDemo {

    public static ZooKeeper zooKeeper;

    public static void main(String[] args) {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState().equals(Event.KeeperState.SyncConnected)) {
                        String path = event.getPath();
                        if (event.getType().equals(Event.EventType.None) && path == null) {
                            try {
                                Stat stat = zooKeeper.exists("/znode_50000000011", true);
                                System.out.println("stat = " + stat);

                            } catch (KeeperException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                        else if (event.getType().equals(Event.EventType.NodeCreated)) {
                            System.out.println("path = " + path);
                            System.out.println("NodeCreated");
                        }
                        else if (event.getType().equals(Event.EventType.NodeDeleted)) {
                            System.out.println("path = " + path);
                            System.out.println("NodeDeleted");
                        }
                        else if (event.getType().equals(Event.EventType.NodeDataChanged)) {
                            System.out.println("path = " + path);
                            System.out.println("NodeDataChanged");
                        }
                        else if (event.getType().equals(Event.EventType.NodeChildrenChanged)) {
                            System.out.println("path = " + path);
                            System.out.println("NodeChildrenChanged");
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
