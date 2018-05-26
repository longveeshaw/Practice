package com.practice.zookeeper.balance.server;

import org.I0Itec.zkclient.ZkClient;

public class RegisterContext {
    private ZkClient zkClient;
    private String nodePath;
    private ServerNodeData nodeData;

    public RegisterContext(ZkClient zkClient, String nodePath, ServerNodeData nodeData) {
        this.zkClient = zkClient;
        this.nodePath = nodePath;
        this.nodeData = nodeData;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public ServerNodeData getNodeData() {
        return nodeData;
    }

    public void setNodeData(ServerNodeData nodeData) {
        this.nodeData = nodeData;
    }
}
