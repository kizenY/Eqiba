package com.kizen.eqiba.server.dao.redis.Impl;

import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * 数据结构：
 * 1.Set online 存储在线用户的sessionId
 * 2.Set break  存储断线用户的sessionId
 * 3.Hash relation sessionId User 存储sessionId对应的用户数据
 * 4.string user.id sessionId 存储用户编号对应的sessionId
 */
@Repository
public class RedisUserDaoImpl  implements RedisUserDao {

    private Logger logger = LoggerFactory.getLogger(RedisUserDaoImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    //key:sessionId  value:userId
    private ValueOperations<String,Integer> users;

    @PostConstruct
    public void init()
    {
            users = redisTemplate.opsForValue();
    }

    @Override
    public boolean exist(String sessionId) {

        if (users.get(sessionId)!=null)
            return true;

        return false;
    }

    @Override
    public void add(String sessionId, User user) {
            users.set(sessionId,user.id,20, TimeUnit.MINUTES);
    }

    @Override
    public User remove(String sessionId) {
        User user = new User();
        user.id = users.get(sessionId);
        users.getOperations().delete(sessionId);
        return user;
    }

}
