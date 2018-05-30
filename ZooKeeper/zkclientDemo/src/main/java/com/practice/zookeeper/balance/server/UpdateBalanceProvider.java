package com.practice.zookeeper.balance.server;

public interface UpdateBalanceProvider {

    void addBalance(int step);

    void reduceBalance(int step);

}
