package com.kizen.eqiba.server.dao.redis.Impl;

import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * 数据结构：
 * 1.Set online 存储在线用户的sessionId
 * 2.Set break  存储断线用户的sessionId
 * 3.Hash relation sessionId User 存储sessionId对应的用户数据
 * 4.string user.id sessionId 存储用户编号对应的sessionId
 */
@Repository
public class RedisUserDaoImpl  implements RedisUserDao {

    private final String KEY_SET_ONLINE = "online";
    private final String KEY_SET_BREAK = "break";
    private final String KEY_HASH_RELATION = "relation";

    private Logger logger = LoggerFactory.getLogger(RedisUserDaoImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private ValueOperations<String,String> valueOperations;
    private HashOperations<String,String,User> hashOperations;
    private SetOperations<String,String> setOperations;

    @PostConstruct
    public void init()
    {
            setOperations =redisTemplate.opsForSet();
            hashOperations=redisTemplate.opsForHash();
            valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public boolean exist(String sessionId) {

        if (setOperations.isMember(KEY_SET_ONLINE,sessionId) || setOperations.isMember(KEY_SET_BREAK,sessionId))
            return true;

        return false;
    }

    @Override
    public String exist(User user) {

         return valueOperations.get(""+user.id);

    }

    @Override
    public boolean existAndUpdate(String sessionId) {

        if (setOperations.isMember(KEY_SET_ONLINE,sessionId))
            return true;
        else if(setOperations.isMember(KEY_SET_BREAK,sessionId))
        {
            //若用户曾处于伪登出表，则刷新至在线表
            setOperations.remove(KEY_SET_BREAK,sessionId);
            setOperations.add(KEY_SET_ONLINE,sessionId);
            return true;
        }
        else
            return false;
    }

    @Override
    public void add(String sessionId, User user) {

        setOperations.add(KEY_SET_ONLINE,sessionId);
        hashOperations.put(KEY_HASH_RELATION,sessionId,user);
        valueOperations.set(""+user.id,sessionId);

    }

    @Override
    public User remove(String sessionId) {
        User user =hashOperations.get(KEY_HASH_RELATION,sessionId);
        if (user==null)
            return null;
        setOperations.remove(KEY_SET_ONLINE,sessionId);
        setOperations.remove(KEY_SET_BREAK,sessionId);
        hashOperations.delete(KEY_HASH_RELATION,sessionId);
        valueOperations.getOperations().delete(user.id+"");
        return user;
    }

    @Override
    public void transferToBreakTable(String sessionId) {

        //将用户从在线表转移至伪登出表
        if (setOperations.isMember(KEY_SET_ONLINE,sessionId))
        {
            setOperations.remove(KEY_SET_ONLINE,sessionId);
            setOperations.add(KEY_SET_BREAK,sessionId);
        }
        //若用户不在在线表中，说明系统出现错误。系统内部打印错误，不向用户报告。
        else logger.error("系统出现错误！非在线用户实行断线操作。");
    }

}
