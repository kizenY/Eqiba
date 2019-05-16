package com.kizen.eqiba.server.service;

import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.entity.dto.EqibaResult;

public interface UserService{
    /**
     * 确认用户是否在线
     * @param sessionId 用户连接凭证
     * @return
     */
    boolean isValid(String sessionId);

    /**
     * 注册用户
     * @param user
     * @return
     */
    EqibaResult register(User user);

    /**
     * 登陆用户
     * @param account 可能为用户名、邮箱、手机号
     * @param password 用户密码
     * @param sessionId 用来判断用户是否已登录
     * @return
     */
    EqibaResult login(String account,String password,String sessionId);

    /**
     * 登出用户
     * @param sessionId
     * @return
     */
    EqibaResult logout(String sessionId);


}
