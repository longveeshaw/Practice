package com.practice.zookeeper.balance.client;

import java.util.List;

public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {

    public T getBalanceItem() {
        return calcBalanceItem(getItemList());
    }

    protected abstract T calcBalanceItem(List<T> itemList);

    protected abstract List<T> getItemList();
}
