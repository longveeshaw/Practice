package com.practice.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

public class GetChildrenSyncDemo  {


    public static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        zooKeeper=new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (state.equals(Event.KeeperState.SyncConnected)) {
                    String path = event.getPath();
                    if (event.getType().equals(Event.EventType.None) && path == null) {
                        try {
                            List<String> children = zooKeeper.getChildren("/", true);
                            System.out.println("children = " + children);

                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else if (event.getType().equals(Event.EventType.NodeChildrenChanged)) {
                        List<String> children = null;
                        try {
                            children = zooKeeper.getChildren("/", true);
                            System.out.println("children = " + children);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);

    }
}
