package websocket;

import com.kizen.eqiba.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.WebSocketConnectionManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class AnalogueCLienmt {


    @Test
    public void test()
    {

        conflictProcess();

    }

    private void normalProcess()
    {
        HttpClient client = new HttpClient();
        final WebsocketClient websocketClient = new WebsocketClient();
        final HttpHeaders headers = new HttpHeaders();
        String sessionID="";
        String account = "kizen";
        String password = "123456";
        sessionID=client.login(account,password);
        System.out.println("sessionId of httpRequest for login"+sessionID);
        headers.add("cookie",sessionID);
        WebSocketConnectionManager manager =websocketClient.connect(headers);

        for (int i =0;i<10;i++)
        {
            try {
                System.out.println(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        manager.stop();
        client.loginOut(sessionID);
    }

    private void conflictProcess()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient customary = new HttpClient();
                final WebsocketClient websocketClient = new WebsocketClient();
                final HttpHeaders headers = new HttpHeaders();
                String sessionID="";
                String account = "kizen";
                String password = "123456";
                sessionID=customary.login(account,password);
                headers.add("cookie",sessionID);
                websocketClient.connect(headers);

            }
        }).start();
        try {
            for (int i =1;i<10;i++){
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开启第二个用户");
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient later = new HttpClient();
                final WebsocketClient websocketClient = new WebsocketClient();
                final HttpHeaders headers = new HttpHeaders();
                String sessionID="";
                String account = "kizen";
                String password = "123456";
                sessionID=later.login(account,password);
                System.out.println("user2 session:"+sessionID);
                headers.add("cookie",sessionID);
                WebSocketConnectionManager manager =websocketClient.connect(headers);
                try {
                    System.out.println("user2运行中");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                manager.stop();
                later.loginOut(sessionID);
                System.out.println("user2终止");
            }
        }).start();
        System.out.println("开始等待user2线程");
        try {
            for (int i =1;i<10;i++){
                System.out.println(i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void acceptMessage()
    {

    }
}
