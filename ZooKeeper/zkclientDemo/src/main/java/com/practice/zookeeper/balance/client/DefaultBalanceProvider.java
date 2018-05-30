package com.practice.zookeeper.balance.client;

import com.practice.zookeeper.balance.server.ServerNodeData;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultBalanceProvider extends AbstractBalanceProvider<ServerNodeData> {

    private String zkServerAddress;

    private String serverDirPath;

    private ZkClient zkClient;

    public DefaultBalanceProvider(String zkServerAddress, String serverDirPath) {
        this.zkServerAddress = zkServerAddress;
        this.serverDirPath = serverDirPath;
        this.zkClient = new ZkClient(zkServerAddress, 5000, 5000, new SerializableSerializer());
    }

    protected ServerNodeData calcBalanceItem(List<ServerNodeData> itemList) {
        if (itemList.size() > 0) {
            Collections.sort(itemList);
            return itemList.get(0);
        }
        return null;
    }

    protected List<ServerNodeData> getItemList() {
        List<ServerNodeData> itemList = new ArrayList<ServerNodeData>();

        List<String> childrenPath = zkClient.getChildren(serverDirPath);

        for (String path : childrenPath) {
            ServerNodeData serverData = zkClient.readData(serverDirPath + "/" + path);
            itemList.add(serverData);
        }
        System.out.println("get server node list "+itemList);
        return itemList;
    }
}
