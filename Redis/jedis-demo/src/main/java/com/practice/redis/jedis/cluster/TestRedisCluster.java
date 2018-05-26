package com.practice.redis.jedis.cluster;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class TestRedisCluster {

    private static JedisCluster jedisCluster;

    @BeforeClass
    public static void before() {


        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("192.168.129.135", 7001));
        nodes.add(new HostAndPort("192.168.129.135", 7002));
        nodes.add(new HostAndPort("192.168.129.135", 7003));
        nodes.add(new HostAndPort("192.168.129.135", 7004));
        nodes.add(new HostAndPort("192.168.129.135", 7005));
        nodes.add(new HostAndPort("192.168.129.135", 7006));

        GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
        cfg.setMaxTotal(1000);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);
        jedisCluster = new JedisCluster(nodes, 1000, 1000, cfg);
    }

    @Test
    public void test() {

        String
                name = jedisCluster.get("name");
        System.out.println("name = " + name);

        String address = jedisCluster.get("address");
        System.out.println("address = " + address);

        String country = jedisCluster.get("country");
        System.out.println("country = " + country);

    }
}
