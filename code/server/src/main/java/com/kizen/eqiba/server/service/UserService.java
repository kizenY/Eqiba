package com.kizen.eqiba.server.service;

import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.entity.dto.EqibaResult;
import org.springframework.web.socket.WebSocketSession;

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

    /**
     *
     * @param sessionId
     * @return
     */
    boolean requestConnecting(String sessionId);

    /**
     * 保存用户的长连接
     * @param socketSession
     */
    void establishedConnection(WebSocketSession socketSession);

    /**
     * 处理出现错误的长连接：多由用户直接终结程序造成
     * @param socketSession
     */
    void handleConnectionError(WebSocketSession socketSession);

    /**
     * 处理被关闭的长连接，清除service对session的持有，使其被回收
     * @param session
     */
    void handleConnectionClosed(WebSocketSession session);
}
