package com.practice.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class GetChildrenAsyncDemo {


    public static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException {

        zooKeeper = new ZooKeeper("127.0.0.1:2181", 4000, new Watcher() {
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                if (state.equals(Event.KeeperState.SyncConnected)) {
                    String path = event.getPath();
                    if (event.getType().equals(Event.EventType.None) && path == null) {
                        zooKeeper.getChildren("/", true, new AsyncCallback.Children2Callback() {
                            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                                System.out.println("rc = [" + rc + "], path = [" + path + "], ctx = [" + ctx + "], children = [" + children + "], stat = [" + stat + "]");
                            }
                        }, "ctx");

                    }

                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);

    }
}
