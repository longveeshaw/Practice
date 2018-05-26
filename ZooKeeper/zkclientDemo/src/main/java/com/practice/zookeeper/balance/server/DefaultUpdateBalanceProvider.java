package com.practice.zookeeper.balance.server;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.apache.zookeeper.data.Stat;

public class DefaultUpdateBalanceProvider implements UpdateBalanceProvider {


    private final ZkClient zkClient;
    private final String currentNodePath;

    public DefaultUpdateBalanceProvider(String currentNodePath, ZkClient zkClient) {
        this.currentNodePath = currentNodePath;
        this.zkClient = zkClient;
    }

    public void addBalance(int step) {
        Stat stat = new Stat();
        ServerNodeData data = zkClient.readData(currentNodePath, stat);
        int updatedBalance = data.getBalance() + step;
        data.setBalance(updatedBalance);
        try {
            zkClient.writeData(currentNodePath, data, stat.getVersion());
            System.out.println(data.getHost()+":"+data.getPort()+" balance add "+step);
        } catch (ZkBadVersionException e) {
            addBalance(step);
        }

    }

    public void reduceBalance(int step) {
        Stat stat = new Stat();
        ServerNodeData data = zkClient.readData(currentNodePath, stat);
        int temp = data.getBalance() - step;
        int updatedBalance = temp >= 0 ? temp : 0;
        data.setBalance(updatedBalance);
        try {
            zkClient.writeData(currentNodePath, data, stat.getVersion());
            System.out.println(data.getHost()+":"+data.getPort()+" balance reduce "+step);

        } catch (ZkBadVersionException e) {
            addBalance(step);
        }

    }
}
