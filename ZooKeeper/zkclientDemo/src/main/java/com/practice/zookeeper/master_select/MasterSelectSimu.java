package com.practice.zookeeper.master_select;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MasterSelectSimu {


    public static void main(String[] args) {
        List<ZkClient> zkClients = new ArrayList<ZkClient>();
        List<WorkServer> workServers = new ArrayList<WorkServer>();

        try {
            for (int i = 0; i < 10; i++) {
                ZkClient zkClient = new ZkClient("127.0.0.1:2181", 6000, 6000, new SerializableSerializer());
                zkClients.add(zkClient);
                RunningData serverData = new RunningData();
                serverData.setCid(i);
                serverData.setName("server_" + i);
                WorkServer workServer = new WorkServer(zkClient, serverData);
                workServers.add(workServer);
                workServer.start();
            }
            System.out.println("enter to exit: ");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (ZkClient zkClient : zkClients) {
                if (zkClient != null) {
                    zkClient.close();
                }
            }

            for (WorkServer workServer : workServers) {
                if (workServer != null) {
                    workServer.stop();
                }
            }
        }
    }
}
