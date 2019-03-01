package com.kizen.eqiba.server.dao.mysql;

import com.kizen.eqiba.server.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SqlUserDao {

    /**
     * 根据用户账户信息在数据库中搜索用户
     * @param account 可能是用户名、邮箱、手机号
     * @return 符合账号的用户编号和密码信息
     */
    User getUserByAccount(@Param("account") String account);

    /**
     * 创建用户
     * @param user 包括信息：用户名、密码、创建时间
     * @return
     */
    void createUser(@Param("user") User user);

    /**
     * 更新用户信息，更新信息至少为一条
     * @param user 单条信息可能为：登录时间、登出时间
     * @return
     */
    void update(@Param("user") User user);
}
