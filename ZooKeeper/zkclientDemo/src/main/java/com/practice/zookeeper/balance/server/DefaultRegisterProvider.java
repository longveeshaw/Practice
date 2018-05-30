package com.practice.zookeeper.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;

public class DefaultRegisterProvider implements RegisterProvider {
    public void register(Object ctx) {
        RegisterContext context=(RegisterContext)ctx;
        ServerNodeData nodeData = context.getNodeData();
        String nodePath = context.getNodePath();
        ZkClient zkClient = context.getZkClient();
        try {

            zkClient.createEphemeral(nodePath, nodeData);
        } catch (ZkNoNodeException e) {
            zkClient.createPersistent(nodePath.substring(0, nodePath.lastIndexOf("/")), true);
            register(ctx);

        }

    }

    public void unRegister(Object ctx) {
        return;
    }
}
