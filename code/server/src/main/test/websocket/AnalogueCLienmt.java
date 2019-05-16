package websocket;

import com.kizen.eqiba.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class AnalogueCLienmt {


    @Test
    public void test()
    {

        normalProcess();

    }

    private void normalProcess()
    {
        HttpClient client = new HttpClient();
        final WebsocketClient websocketClient = new WebsocketClient();
        final HttpHeaders headers = new HttpHeaders();
        String sessionID="";
        String account = "Kiten";
        String password = "123456";
        client.register(account,password);
        sessionID=client.login(account,password);
        System.out.println("sessionId of httpRequest for login"+sessionID);
        client.loginOut(sessionID);
    }


}
