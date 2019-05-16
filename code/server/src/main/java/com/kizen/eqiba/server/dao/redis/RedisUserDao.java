package com.kizen.eqiba.server.dao.redis;


import com.kizen.eqiba.server.entity.User;
import org.springframework.stereotype.Repository;

/**
 *  使用redis管理user
 *  表群分为两张表：
 *  1.在线用户表 存储保持了长连接的用户
 *  2.伪登出表   存储暂时断开了长连接的用户，服务器仍认为其是在线的
 *  3.辅助表    为了解决根据key-value的双向查找，创建的以用户编号为Key的表
 */

public interface RedisUserDao {
    /**
     * 根据session凭证查询用户是否存在于表群数据中
     * 若用户存在于伪登出表中，则将其转移至在线用户表
     * @param sessionId
     * @return 是否存在表中
     */
    boolean exist(String sessionId);

    /**
     * 将用户添加到在线表中
     * @param sessionId
     * @param user
     */
    void add(String sessionId,User user);

    /**
     * 将用户数据移出表群
     * @param sessionId
     * @return 被移出的用户信息
     */
    User remove(String sessionId);

}
