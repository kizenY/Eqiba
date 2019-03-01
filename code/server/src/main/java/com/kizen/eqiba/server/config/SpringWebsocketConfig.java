package com.kizen.eqiba.server.config;

import com.kizen.eqiba.server.controller.Interceptor.SpringWebsocketInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class SpringWebsocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(),"websocket").addInterceptors(myInterceptors()).setAllowedOrigins("*");
    }

    @Bean
    public SpringWebsocketHandler myHandler() {
        return new SpringWebsocketHandler();
    }

    @Bean
    public SpringWebsocketInterceptor myInterceptors(){return new SpringWebsocketInterceptor();}

}
