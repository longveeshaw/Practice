package com.practice.zookeeper.subscribe;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubscribeZkClient {


    public static final String CONFIG_PATH = "/config";
    public static final String SERVERS_PATH = "/servers";
    public static final String CMD_PATH = "/cmd";
    public static final String ZK_URL = "127.0.0.1:2181";

    public static void main(String[] args) {
        new SubscribeZkClient().go();
    }

    private void go() {
        List<WorkServer> workServers = new ArrayList<WorkServer>(10);
        List<ZkClient> zkClients = new ArrayList<ZkClient>(10);
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setDbUsername("root");
        serverConfig.setDbPsw("admin");
        serverConfig.setDbUrl("jdbc:mysql://127.0.0.1:3306/db");
        ManageServer manageServer = null;
        try {
            ZkClient mngZkClient = new ZkClient(ZK_URL, 5000, 5000, new BytesPushThroughSerializer());
            manageServer = new ManageServer(serverConfig, CONFIG_PATH, SERVERS_PATH, CMD_PATH, mngZkClient);
            zkClients.add(mngZkClient);
            manageServer.start();

            for (int i = 0; i < 5; i++) {
                ZkClient zkClient = new ZkClient(ZK_URL, 5000, 5000, new BytesPushThroughSerializer());
                ServerData serverData = new ServerData();
                serverData.setAddress("192.168.1." + i);
                serverData.setId(""+ i);
                serverData.setName("workServer#" + i);
                WorkServer workServer = new WorkServer(zkClient, serverConfig, serverData, CONFIG_PATH, SERVERS_PATH);
                workServers.add(workServer);
                zkClients.add(zkClient);
                workServer.start();
            }

            System.out.println("enter ENTER to quit: ");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("start to stop or close all!");
            try {
                if (manageServer != null) {
                    manageServer.stop();
                }
                for (WorkServer workServer : workServers) {
                    workServer.stop();
                }
                for (ZkClient zkClient : zkClients) {
                    zkClient.close();
                }
            } catch (ZkInterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
