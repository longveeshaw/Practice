package com.practice.zookeeper.mastersel;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;

public class WorkServer extends LeaderSelectorListenerAdapter implements Closeable {

    private LeaderSelector leaderSelector;

    private String name;

    private RunningListener runningListener;

    public WorkServer(CuratorFramework client, String name, String leaderPath, RunningListener runningListener) {
        this.name = name;
        this.leaderSelector = new LeaderSelector(client, leaderPath, this);
        this.runningListener = runningListener;
    }

    public void start() {
        leaderSelector.start();
        runningListener.processStart(this.name);
    }

    public void close() throws IOException {
        leaderSelector.close();
        runningListener.processStop(this.name);
    }

    public void takeLeadership(CuratorFramework client) throws Exception {
        try {
            runningListener.processActiveEnter(this.name);
            System.out.println("taken leader ship!\npreparing a while");
            Thread.sleep(5);
            System.out.println("preparation done");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            runningListener.processActiveExit(this.name);
        }

    }

}
