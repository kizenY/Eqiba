package websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizen.eqiba.server.entity.dto.EqibaResult;
import org.glassfish.grizzly.http.HttpHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring/spring-*.xml"))
public class WebsocketClient {

    private WebSocketHandler handler = new WebSocketHandler() {
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            System.out.println("连接成功");
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            String code;
            System.out.println("接收到信息："+message.getPayload()+"\n接受者："+session.getAttributes().get("sessionId"));
            code = EqibaResult.getResultFromJsonString(message.getPayload().toString()).code;
            System.out.println("code:"+code);
            if (code.equals("131"))
                session.close();
//            ObjectMapper objectMapper = new ObjectMapper();
//            EqibaResult result =objectMapper.readValue(message.getPayload().toString(), EqibaResult.class);
//            if (result.code.equals("131"))
//                session.close();
//            System.out.println(message.getPayload());
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            System.out.println("ERROR");
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            System.out.println("CLOSE");
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }
    };
    @Test
    public void test()
    {

        connect(null);

    }

    public WebSocketConnectionManager connect(HttpHeaders httpHeader)
    {



        String uri ="ws://localhost:8080/server_war/websocket";
        StandardWebSocketClient client = new StandardWebSocketClient();
        WebSocketConnectionManager manager = new WebSocketConnectionManager(client, handler, uri);
        manager.setHeaders(httpHeader);
        manager.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return manager;


    }



}
