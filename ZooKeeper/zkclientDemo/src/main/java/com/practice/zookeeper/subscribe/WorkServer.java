package com.practice.zookeeper.subscribe;

import com.alibaba.fastjson.JSON;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

/**
 * listening server ip list
 */
public class WorkServer {

    private ServerConfig serverConfig;

    private ServerData serverData;

    private String configPath;

    private String serversPath;

    private IZkDataListener configNodeListener;

    private ZkClient zkClient;

    public WorkServer() {
    }

    public WorkServer(ZkClient zkClient, ServerConfig serverConfig, ServerData serverData, String configPath, String serversPath) {
        this.zkClient = zkClient;
        this.serverConfig = serverConfig;
        this.serverData = serverData;
        this.configPath = configPath;
        this.serversPath = serversPath;

        this.configNodeListener = new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                String json = new String((byte[]) data);
                ServerConfig configLocal = JSON.parseObject(json, ServerConfig.class);
                updateServerConfig(configLocal);

            }

            public void handleDataDeleted(String dataPath) throws Exception {

            }
        };
    }

    public void start() {
        System.out.println("work server_"+serverData.getId()+" started");
        registerSelf();
        zkClient.subscribeDataChanges(configPath, configNodeListener);
    }

    private void registerSelf() {
        try {
            zkClient.createEphemeral(serversPath + "/" + serverData.getAddress(), JSON.toJSONString(serverData).getBytes());
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(serversPath,true);
            registerSelf();
        }
    }

    public void stop() {
        System.out.println("work server_" + serverData.getId() + " stopped");
        zkClient.unsubscribeDataChanges(configPath, configNodeListener);
    }

    private void updateServerConfig(ServerConfig configLocal) {
        System.out.println("work server_" + serverData.getId() + " server config updated: "+configLocal);
        this.serverConfig = configLocal;
    }
}
