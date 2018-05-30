package com.practice.zookeeper.master_select;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkServer {

    private static final String MASTER_PATH = "/master";
    private final ZkClient zkClient;
    private RunningData serverData;
    private RunningData masterData;
    private boolean running;
    private IZkDataListener zkDataListener;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public long DELAY_TIME = 5;

    public WorkServer(ZkClient zkClient, final RunningData serverData) {
        this.zkClient = zkClient;
        this.serverData = serverData;
        this.zkDataListener = new IZkDataListener() {

            public void handleDataChange(String dataPath, Object data) throws Exception {
                //do nothing
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                if (masterData != null && masterData.getName().equals(serverData.getName())) {
                    takeMaster();
                } else {
                    scheduledExecutorService.schedule(new Runnable() {
                        public void run() {
                            takeMaster();
                        }
                    }, DELAY_TIME, TimeUnit.SECONDS);

                }
            }
        };
    }


    public void start() {
        if (this.running) {
            throw new RuntimeException("the server has already started");
        }
        zkClient.subscribeDataChanges(MASTER_PATH, zkDataListener);
        running = true;
        takeMaster();

    }

    private boolean checkMaster() {
        try {
            RunningData zkData = zkClient.readData(MASTER_PATH);
            masterData = zkData;
            if (serverData.getName().equals(zkData.getName())) {
                return true;
            }
        } catch (ZkNoNodeException e) {
            return false;
        } catch (ZkInterruptedException e) {
            return checkMaster();
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    public void stop() {
        if (!this.running) {
            throw new RuntimeException("the server has already stopped");
        }
        running = false;
        zkClient.unsubscribeDataChanges(MASTER_PATH, zkDataListener);
        releaseMaster();
        scheduledExecutorService.shutdown();
    }

    private void releaseMaster() {
        if (checkMaster()) {
            zkClient.delete(MASTER_PATH);
        }
    }

    private void takeMaster() {
        try {
            zkClient.create(MASTER_PATH, serverData, CreateMode.EPHEMERAL);
            masterData = serverData;
            System.out.println(serverData.getName() + " is master");
            scheduledExecutorService.schedule(new Runnable() {
                public void run() {
                    if (checkMaster()) {// demo master release
                        releaseMaster();
                    }
                }
            }, 7, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
            RunningData zkData = zkClient.readData(MASTER_PATH);
            if (zkData != null) {
                masterData = zkData;
                System.out.println(serverData.getName() + " is master");

            } else {
                takeMaster();
            }
        } catch (RuntimeException ignored) {
        }

    }


}
