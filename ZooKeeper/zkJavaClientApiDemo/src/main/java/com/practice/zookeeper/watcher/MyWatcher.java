package com.practice.zookeeper.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class MyWatcher implements Watcher {
    public void process(WatchedEvent event) {
        System.out.println("event = [" + event + "]");
        if (event.getState().equals(Event.KeeperState.SyncConnected)) {
            Event.KeeperState state = event.getState();
            System.out.println("state = " + state);

        }
    }
}
