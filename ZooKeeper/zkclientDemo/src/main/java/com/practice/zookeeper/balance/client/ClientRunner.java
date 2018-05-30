package com.practice.zookeeper.balance.client;

import com.practice.zookeeper.balance.server.ServerNodeData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientRunner {


    public static final int N_THREADS = 4;

    public static void main(String[] args) {
       final List<Client> clients = new ArrayList<Client>();

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
            final BalanceProvider<ServerNodeData> balanceProvider = new DefaultBalanceProvider("127.0.0.1:2181", "/servers");
            for (int i=0;i<N_THREADS;i++) {
                final int I=i;
                executorService.execute(new Runnable() {
                    public void run() {
                        ClientImpl client = new ClientImpl(balanceProvider, "client_" + I);
                        clients.add(client);
                        client.connect();

                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("input ENTER to quit:");
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            for (Client client : clients) {
                try {
                    client.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
