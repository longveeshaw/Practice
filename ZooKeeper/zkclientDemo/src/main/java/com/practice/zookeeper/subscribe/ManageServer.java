package com.practice.zookeeper.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.List;


public class ManageServer {
    private ServerConfig serverConfig;

    private String configPath;

    private String serversPath;

    private String cmdPath;

    private ZkClient zkClient;

    private IZkDataListener cmdDataListener;

    private IZkChildListener serversChildListener;

    private List<String> workServerList;

    public ManageServer() {
    }

    public ManageServer(ServerConfig serverConfig, String configPath, String serversPath, String cmdPath, ZkClient zkClient) {
        this.serverConfig = serverConfig;
        this.configPath = configPath;
        this.serversPath = serversPath;
        this.cmdPath = cmdPath;
        this.zkClient = zkClient;
        this.serversChildListener = new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                workServerList = currentChilds;
                System.out.println("workServerList updated: " + workServerList);
            }
        };
        this.cmdDataListener = new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                String cmd = new String((byte[]) data);
                System.out.println("execute cmd: "+cmd);
                execCmd(cmd);
            }

            public void handleDataDeleted(String dataPath) throws Exception {

            }
        };
    }

    public void start() {
        zkClient.subscribeDataChanges(cmdPath, cmdDataListener);
        zkClient.subscribeChildChanges(serversPath, serversChildListener);
    }

    public void stop() {
        zkClient.unsubscribeDataChanges(cmdPath, cmdDataListener);
        zkClient.unsubscribeChildChanges(serversPath, serversChildListener);
    }

    private void execCmd(String cmd) {
        if ("create".equalsIgnoreCase(cmd)) {
            execCreate();
        } else if ("list".equalsIgnoreCase(cmd)) {
            execList();
        } else if ("modify".equalsIgnoreCase(cmd)) {
            execModify();
        }
    }

    private void execList() {
        System.out.println("work server list: " + workServerList);
    }

    private void execModify() {
        serverConfig.setDbUsername(serverConfig.getDbUsername() + "_modified");
        try {
            zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
            System.out.println("node "+configPath+" wrote data: "+serverConfig);

        } catch (ZkNoNodeException e) {
            execCreate();
        }
    }

    private void execCreate() {
        if (!zkClient.exists(configPath)) {
            try {
                zkClient.createPersistent(configPath, JSON.toJSONString(serverConfig).getBytes());
                System.out.println("node "+configPath+" created");
            } catch (ZkNodeExistsException e) {
                zkClient.writeData(configPath, JSON.toJSONString(serverConfig).getBytes());
            } catch (ZkNoNodeException e) {
                zkClient.createPersistent(configPath.substring(0, configPath.lastIndexOf("/")), true);
                execCreate();
            }
        }else {
            System.out.println(configPath +" already existed");
        }

    }
}
