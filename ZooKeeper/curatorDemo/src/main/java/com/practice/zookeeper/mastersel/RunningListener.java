package com.practice.zookeeper.mastersel;

public interface RunningListener {
    void processStart(Object ctx);

    void processStop(Object ctx);

    void processActiveEnter(Object ctx);

    void processActiveExit(Object ctx);
}
