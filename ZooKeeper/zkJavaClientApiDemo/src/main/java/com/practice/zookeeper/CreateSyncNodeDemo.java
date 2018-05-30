package com.practice.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class CreateSyncNodeDemo {


    static ZooKeeper zooKeeper;
    public static void main(String[] args) throws IOException, InterruptedException {
        zooKeeper=new ZooKeeper("127.0.0.1:2181",5000,new CreateSyncNodeWatcher());

        Thread.sleep(Integer.MAX_VALUE);

    }


}
class CreateSyncNodeWatcher implements Watcher{
    public void process(WatchedEvent event) {
        if (event.getState().equals(Event.KeeperState.SyncConnected)) {
            try {
                String path = CreateSyncNodeDemo.zooKeeper.create("/syncPerNode", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
                System.out.println("path = " + path);

            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}