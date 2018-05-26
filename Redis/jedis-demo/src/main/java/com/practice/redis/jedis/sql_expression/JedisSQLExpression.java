package com.practice.redis.jedis.sql_expression;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.practice.redis.jedis.model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * select * from user where age=18 ;
 * select * from user where age=18 and gender='m'
 */
public class JedisSQLExpression {

    @BeforeClass
    public static void prepareData() {

        Jedis jedis = new Jedis("192.168.129.135", 6379);

        jedis.flushAll();

        Map<String, String> hash = new HashMap<String, String>(32);

        String uid = null;
        User user = null;


        uid = UUID.randomUUID().toString();
        user = new User(uid, 17, 'm');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_GENDER_M", uid);
        jedis.sadd("SYS_USER_GENDER_F", uid);

        uid = UUID.randomUUID().toString();
        user = new User(uid, 18, 'f');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_AGE_18", uid);
        jedis.sadd("SYS_USER_GENDER_F", uid);

        uid = UUID.randomUUID().toString();
        user = new User(uid, 17, 'f');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_GENDER_F", uid);

        uid = UUID.randomUUID().toString();
        user = new User(uid, 18, 'm');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_AGE_18", uid);
        jedis.sadd("SYS_USER_GENDER_M", uid);

        uid = UUID.randomUUID().toString();
        user = new User(uid, 18, 'f');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_AGE_18", uid);
        jedis.sadd("SYS_USER_GENDER_F", uid);

        uid = UUID.randomUUID().toString();
        user = new User(uid, 18, 'm');
        hash.put(uid, JSON.toJSONString(user));
        jedis.sadd("SYS_USER_AGE_18", uid);
        jedis.sadd("SYS_USER_GENDER_M", uid);


        jedis.hmset("SYS_USER_TABLE", hash);


    }

    /**
     * select * from user where age=18 ;
     */
    @Test
    public void sqlSelectWhere() {
        Jedis jedis = new Jedis("192.168.129.135", 6379);
        Set<String> uidOf18 = jedis.smembers("SYS_USER_AGE_18");
        for (String uid : uidOf18) {
            String userJsonStr = jedis.hget("SYS_USER_TABLE", uid);
            User user = JSON.parseObject(userJsonStr, User.class);
            System.out.println("user = " + user);
        }

    }

    /**
     * select * from user where age=18 and gender='m'
     */
    @Test
    public void sqlWhereAnd() {
        Jedis jedis = new Jedis("192.168.129.135", 6379);

        Set<String> uids = jedis.sinter("SYS_USER_AGE_18", "SYS_USER_GENDER_M");
        for (String uid : uids) {
            String userJsonStr = jedis.hget("SYS_USER_TABLE", uid);
            User user = JSON.parseObject(userJsonStr, User.class);
            System.out.println("user = " + user);
        }
    }
}
