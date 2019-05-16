package com.kizen.eqiba.server.config;

import com.kizen.eqiba.server.entity.dto.EqibaResult;
import com.kizen.eqiba.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class SpringWebsocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserService userService ;
    private Logger logger = LoggerFactory.getLogger(SpringWebsocketHandler.class);
    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {

        logger.info("连接成功");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("连接错误"+session.getAttributes().get("sessionId"));
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("连接关闭"+session.getAttributes().get("sessionId"));
        super.afterConnectionClosed(session, status);
    }
}
