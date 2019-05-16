package com.eqiba.kizen.client.model;

import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.impl.UserModelImpl;

import java.io.IOException;

public interface UserModel extends MyModel{


    //用户信息管理系列URL的前缀
    String URL_USER= MyModel.PROTOCOL_HTTP+URL_HOME+"user/";

    /**
     * 注册
     * @param account 用户名
     * @param password 密码
     * @return
     * @throws IOException
     * @throws NullBodyException
     */
    EqibaResult register(String account, String password) throws IOException,NullBodyException;

    /**
     * 登录
     * @param account 用户名/手机号/邮箱
     * @param password 密码
     * @return
     * @throws IOException
     * @throws NullBodyException
     */
    EqibaResult<User> login(String account, String password) throws IOException,NullBodyException;

    /**
     * 登出
     * @param sessionId
     * @return
     */
    EqibaResult logout(String sessionId) throws IOException,NullBodyException;

    /**
     * 向数据库存新的用户信息
     * @param user 包含用户所有信息
     * @throws IOException
     * @throws DatabaseException
     */
    void addUser(User user) throws DatabaseException;

    /**
     * 删除数据库中的用户信息
     * @param user 至少包含id信息
     * @throws DatabaseException
     */
    void deleteUser(User user) throws DatabaseException;

    /**
     * 取出已唯一的已登录的用户信息
     * @throws DatabaseException
     */
    User getUser() throws DatabaseException;
}
