package com.kizen.eqiba.server.controller.Interceptor;

import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理需要验证用户身份的请求
 */
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    /**
     * 在请求之前对用户的session作出鉴定，判断其是否登陆。若用户未登陆，则直接返回错误。
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if (!userService.isValid(httpServletRequest.getSession().getId()))
            return false;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
