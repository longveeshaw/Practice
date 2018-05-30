package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;

public class NodeChildListener {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000).retryPolicy(new RetryNTimes(5, 1000)).build();
        client.start();

        PathChildrenCache childrenCache = new PathChildrenCache(client, "/curatorNode", true);

        childrenCache.start();
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("data = " + pathChildrenCacheEvent.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("data = " + pathChildrenCacheEvent.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("data = " + pathChildrenCacheEvent.getData());
                        break;
                    default:
                        break;
                }
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
