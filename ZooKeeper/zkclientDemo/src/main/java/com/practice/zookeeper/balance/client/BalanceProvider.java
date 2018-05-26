package com.practice.zookeeper.balance.client;

public interface BalanceProvider<T> {
    T getBalanceItem();
}
