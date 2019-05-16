package com.kizen.eqiba.server.service.impl;

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
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final String CODE_FIRST = "1";
    @Autowired
    private SqlUserDao sqlUserDao;
    @Autowired
    private RedisUserDao redisUserDao;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
            return new EqibaResult(CODE+"1","注册失败！用户名重复");
        //创建用户
        user.createTime = new Timestamp(new Date().getTime());
        sqlUserDao.createUser(user);

        return new EqibaResult(CODE+"0","注册成功！");
    }

    @Override
    public EqibaResult<User> login(String account,String password,String sessionId) {
        final String CODE =CODE_FIRST+"1";
        //验证用户信息
        User res =sqlUserDao.getUserByAccount(account);
        if (res==null)
            return new EqibaResult<>(CODE+"1","登陆失败！用户名或手机号或邮箱不存在！");
        if (!res.password.equals(password))
            return new EqibaResult<>(CODE+"2","登陆失败！密码错误！");

        //装载预登录成功数据
        List<User> singleUser = new ArrayList<>(1);
        singleUser.add(res);

        //执行登陆操作
        loginOp(res,sessionId);

        return new EqibaResult<>(CODE + "0", "登陆成功！", singleUser);

    }

    @Override
    public EqibaResult logout(String sessionId) {
        final String CODE = CODE_FIRST+"2";
        //检查是否已登出
        if (!redisUserDao.exist(sessionId))
            return new EqibaResult(CODE+"0","登出成功！");
        //执行登出操作
        logoutOp(sessionId);
        return new EqibaResult(CODE+"0","登出成功！");
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
        //若重复登录则先移出
        if (user.sessionId!=null && redisUserDao.exist(user.sessionId))
            redisUserDao.remove(user.sessionId);
        //添加
        redisUserDao.add(sessionId,user);
        User update = new User();
        update.id = user.id;
        update.loginTime = new Timestamp(new Date().getTime());
        update.sessionId = sessionId;
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

}
