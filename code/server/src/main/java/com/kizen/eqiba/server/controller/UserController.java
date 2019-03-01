package com.kizen.eqiba.server.controller;

import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.entity.dto.EqibaResult;
import com.kizen.eqiba.server.exception.NullParamterExcpetion;
import com.kizen.eqiba.server.exception.WrongParameterException;
import com.kizen.eqiba.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST,value = "register",produces = "application/json")
    @ResponseBody
    public EqibaResult register(@RequestBody User user)throws NullParamterExcpetion
    {

        registerFormat(user);

        return userService.register(user);
    }

    /**
     * 用户登陆
     * @param msg 包含用户的账户和密码
     * @return 用户信息
     */
    @RequestMapping(method = RequestMethod.POST,value = "login",produces = "application/json")
    @ResponseBody
    public EqibaResult<User> login(HttpServletRequest request,@RequestBody Map<String,String> msg)throws NullParamterExcpetion
    {

        String account = msg.get("account");
        String password = msg.get("password");

        loginFormat(account,password);

        return userService.login(account,password,request.getSession(true).getId());
    }

    @RequestMapping(method = RequestMethod.GET,value = "needOnline/logout")
    @ResponseBody
    public EqibaResult logout(HttpServletRequest request)
    {
        return userService.logout(request.getSession().getId());
    }


    //初步检查数据是否合规

    private void registerFormat(User user) throws NullParamterExcpetion,WrongParameterException
    {
        String wrong = "注册失败";
        if (user.username==null || user.password==null)
            throw new NullParamterExcpetion(wrong);
        if (user.username.length()==0 || user.password.length()==0)
            throw new NullParamterExcpetion(wrong);
    }

    private void loginFormat(String account,String password) throws NullParamterExcpetion
    {
        String wrong = "登陆失败";
        if (account==null || password==null)
            throw new NullParamterExcpetion(wrong);
        if (account.length()==0 || password.length()==0)
            throw new NullParamterExcpetion(wrong);
    }

}
