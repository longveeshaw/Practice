package com.practice.zookeeper.balance.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRunner {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 2; i++) {
            final int portInc=i;
            executorService.execute(new Runnable() {
                public void run() {
                    ServerNodeData serverNodeData = new ServerNodeData();
                    serverNodeData.setBalance(0);
                    serverNodeData.setHost("127.0.0.1");
                    serverNodeData.setPort(6000 + portInc);
                    new ServerImpl("127.0.0.1:2181", "/servers", serverNodeData).bind();
                }
            });
        }
    }
}
