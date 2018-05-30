package com.practice.zookeeper.mastersel;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MasterSelDemo {

    public static void main(String[] args) {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<WorkServer> servers = Lists.newArrayList();
        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(10, 6));
                clients.add(client);
                RunningListener listener=new RunningListener() {
                    public void processStart(Object ctx) {
                        System.out.println(ctx+" started");
                    }

                    public void processStop(Object ctx) {
                        System.out.println(ctx+" stopped");

                    }

                    public void processActiveEnter(Object ctx) {
                        System.out.println(ctx+" active");

                    }

                    public void processActiveExit(Object ctx) {
                        System.out.println(ctx+" exit");

                    }
                };
                WorkServer workServer = new WorkServer(client, "server_" + i, "/master",listener);
                servers.add(workServer);
                client.start();
                workServer.start();
            }

            System.out.println("enter to quit:\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            for (WorkServer server : servers) {
                    CloseableUtils.closeQuietly(server);
            }

            for (CuratorFramework client : clients) {
                CloseableUtils.closeQuietly(client);
            }

        }
    }
}
