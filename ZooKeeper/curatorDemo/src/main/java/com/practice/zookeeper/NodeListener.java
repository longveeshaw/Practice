package com.practice.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;

public class NodeListener {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1").connectionTimeoutMs(5000)
                .sessionTimeoutMs(5000).retryPolicy(new RetryNTimes(5, 1000)).build();
        client.start();

        final NodeCache nodeCache = new NodeCache(client, "/curatorNode");
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            public void nodeChanged() throws Exception {
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println("new String(data) = " + new String(data));

            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
