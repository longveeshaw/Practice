package com.practice.zookeeper;

import com.practice.zookeeper.watcher.MyWatcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class CreateSessionDemo {

    private static ZooKeeper zooKeeper;
    public static void main(String[] args) throws IOException, InterruptedException {

        zooKeeper=new ZooKeeper("127.0.0.1:2181",5000,new MyWatcher());

        System.out.println("zooKeeper = " + zooKeeper);
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("state = " + state);
        Thread.sleep(Integer.MAX_VALUE);
    }


}
