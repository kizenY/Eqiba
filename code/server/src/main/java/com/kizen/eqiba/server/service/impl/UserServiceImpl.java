package com.kizen.eqiba.server.service.impl;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizen.eqiba.server.dao.mysql.SqlUserDao;
import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.entity.dto.EqibaResult;
import com.kizen.eqiba.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final String CODE_FIRST = "1";
    @Autowired
    private SqlUserDao sqlUserDao;
    @Autowired
    private RedisUserDao redisUserDao;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private Map<String,WebSocketSession> onlineSessions = new ConcurrentHashMap<>();

    @Override
    public boolean isValid(String sessionId) {
        return redisUserDao.exist(sessionId);
    }

    @Override
    public EqibaResult register(User user) {
        final String CODE=CODE_FIRST+"0";
        //查重
        User res =sqlUserDao.getUserByAccount(user.username);
        if (res!=null)
            return new EqibaResult(CODE+"1","注册失败！用户名重复",null);
        //创建用户
        user.createTime = new Timestamp(new Date().getTime());
        sqlUserDao.createUser(user);

        return new EqibaResult(CODE+"0","注册成功！",null);
    }

    @Override
    public EqibaResult login(String acount,String password,String sessionId) {
        logger.info("login:"+sessionId);
        final String CODE =CODE_FIRST+"1";
        //验证用户信息
        User res =sqlUserDao.getUserByAccount(acount);

        if (res==null)
            return new EqibaResult(CODE+"1","登陆失败！用户名或手机号或邮箱不存在！",null);
        if (!res.password.equals(password))
            return new EqibaResult(CODE+"2","登陆失败！密码错误！",null);

        //检查用户是否已登录
        String resSeId =redisUserDao.exist(res);

        //装载预登录成功数据
        List<User> singleUser = new ArrayList<>(1);
        singleUser.add(res);

        //用户未登录
        if (resSeId==null)
            //执行登陆操作
            loginOp(res,sessionId);

        //用户重复登陆
        else if (resSeId.equals(sessionId))
            logger.error("系统出现问题，同一用户使用同一凭证再次登陆！");

        //存在不同连接登陆同个用户情况，执行顶号操作
        else
        {
            forceLogout(resSeId);
            loginOp(res,sessionId);
        }
        return new EqibaResult(CODE+"0","登陆成功！",singleUser);

    }

    @Override
    public EqibaResult logout(String sessionId) {
        final String CODE = CODE_FIRST+"2";
        //检查是否已登出
        if (!redisUserDao.exist(sessionId))
            return new EqibaResult(CODE+"0","登出成功！",null);
        //执行登出操作
        logoutOp(sessionId);
        return new EqibaResult(CODE+"0","登出成功！",null);
    }

    @Override
    public boolean requestConnecting(String sessionId) {
        return redisUserDao.existAndUpdate(sessionId);
    }

    @Override
    public void establishedConnection(WebSocketSession socketSession) {
        logger.info("establishedConnection");
        // ！！session获取方式待查！
        onlineSessions.put(getHttpSessionIdFromWebsocket(socketSession),socketSession);
    }


    @Override
    public void handleConnectionError(WebSocketSession socketSession) {
        //若连接出现错误且连接关闭，系统认为是用户主动结束客户端造成
        if (!socketSession.isOpen())
            //将用户设置为暂时离线状态
            redisUserDao.transferToBreakTable(getHttpSessionIdFromWebsocket(socketSession));

    }

    @Override
    public void handleConnectionClosed(WebSocketSession session) {
        onlineSessions.remove(getHttpSessionIdFromWebsocket(session));
    }

    /**
     * 1.断开该用户的当前长连接
     * 2.登出用户
     * @param sessionId
     */
    private void forceLogout(String sessionId)
    {
        logger.info("强制登出"+sessionId);
        disconnect(sessionId);
        logoutOp(sessionId);
    }

    /**
     * 该方法完成两个操作
     * 1.在内存中存入用户信息
     * 2.更新用户登陆时间
     * @param user
     * @param sessionId
     */
    private void loginOp(User user,String sessionId)
    {
        redisUserDao.add(sessionId,user);
        User update = new User();
        update.id = user.id;
        update.loginTime = new Timestamp(new Date().getTime());
        sqlUserDao.update(update);
    }

    /**
     * 该方法完成两个操作
     * 1.在内存中清楚用户信息
     * 2.更新用户登出时间
     * @param sessionId
     */
    private void logoutOp(String sessionId)
    {
        User redisUser = redisUserDao.remove(sessionId);
        User sqlUser = new User();
        sqlUser.id = redisUser.id;
        sqlUser.logoutTime=new Timestamp(new Date().getTime());
        sqlUserDao.update(sqlUser);
    }

    /**
     * 主动关闭用户连接
     * 1.发送提示至客户端，提醒客户端连接过期，希望客户端主动关闭连接
     * 2.放弃保存此连接
     * @param sessionId
     */
    private void disconnect(String sessionId)
    {
        EqibaResult result =new EqibaResult("131","您的账号存在顶号行为！请尽快更改密码！",null);

        WebSocketSession session = onlineSessions.get(sessionId);
        logger.info("disconnect"+sessionId);
        logger.info("session池");
        for (String key:onlineSessions.keySet())
            logger.info(key);

        try {
            session.sendMessage(new TextMessage(result.toString()));
        }catch (IOException e)
        {
            //如果客户端无法收到提示信息，服务器直接关闭连接，避免造成资源浪费
            try{
                session.close();
            }catch (IOException e1)
            {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        //避免违规用户收到信息但拒绝关闭连接
        onlineSessions.remove(sessionId);
    }

    /**
     * 提取websocketSession中握手时http请求的session
     * @param socketSession
     * @return
     */
    private String getHttpSessionIdFromWebsocket(WebSocketSession socketSession)
    {
        return socketSession.getAttributes().get("sessionId").toString();
    }
}
