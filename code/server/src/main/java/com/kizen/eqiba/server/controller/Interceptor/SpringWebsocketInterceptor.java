package com.kizen.eqiba.server.controller.Interceptor;

import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class SpringWebsocketInterceptor extends HttpSessionHandshakeInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手");
        String sessionID = getSessionId(request);
        if (!userService.requestConnecting(sessionID))
            return false;
        attributes.put("sessionId",sessionID);
        return super.beforeHandshake(request, response, wsHandler, attributes);

    }

    private String getSessionId(ServerHttpRequest request)
    {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        System.out.println(servletRequest.getSession().getId());
        return servletRequest.getSession().getId();
    }
}
