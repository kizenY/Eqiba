package websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizen.eqiba.server.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring/spring-*.xml"))
public class HttpClient {

    @Test
    public void test() {

        loginOut("JSESSIONID=8F4DEB70341A573391B8EA1E348D46E8");

    }

    public String  login(String account ,String password) {

        String loginUrl = "http://localhost:8080/server_war/user/login";
        String sessionID="";

        Map<String,String> msg = new HashMap<>();

        msg.put("password",password);
        msg.put("account",account);

        try {
            sessionID =doPost(loginUrl, new ObjectMapper().writeValueAsString(msg),sessionID);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return sessionID;
    }

    public void register(String account,String password)
    {
        String registerUrl = "http://localhost:8080/server_war/user/register";
        String sessionID = "";
        User user = new User();
        user.username = account;
        user.password = password;
        try {
            doPost(registerUrl, new ObjectMapper().writeValueAsString(user),sessionID);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loginOut(String sessionId)
    {
        String loginOutUrl = "http://localhost:8080/server_war/user/needOnline/logout";

        try {
            doGet(loginOutUrl,sessionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String doGet(String url,String sessionID) throws IOException {
        HttpURLConnection connection;

        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        setConnectionConfig(connection);

        setHeader(connection,"cookie",sessionID);

        printResult(connection.getInputStream());

        sessionID=printHeader(connection);

        connection.disconnect();

        return sessionID;
    }

    public String doPost(String url, String data,String sessionID) throws IOException {
        HttpURLConnection connection;

        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        setConnectionConfig(connection);

        setHeader(connection,"cookie",sessionID);

        sendMessage(connection.getOutputStream(), data);

        printResult(connection.getInputStream());

        sessionID=printHeader(connection);

        connection.disconnect();

        return sessionID;


    }

    private void setConnectionConfig(HttpURLConnection connection) {
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setUseCaches(true);

    }

    private void setHeader(HttpURLConnection connection, String key, String value) {
        println("\n设置请求头：\nkey:" + key + "\nvalue:" + value);
        connection.setRequestProperty(key,value);

    }

    private String printHeader(HttpURLConnection connection) {
        println("\n打印请求头\n");
        String sessionID = new String();
        Map<String, List<String>> map = connection.getHeaderFields();
        for (String key : map.keySet()) {
            String value=map.get(key).get(0);
            println("key:" + key + "\nvalue:"+value);
            if ("Set-Cookie".equals(key))
                sessionID= value.substring(0,value.indexOf(";"));
        }
        return sessionID;
    }

    private void sendMessage(OutputStream outputStream, String data) throws IOException {
        println("\n发送消息\n消息内容：" + data);
        outputStream.write(data.getBytes("UTF-8"));
        outputStream.close();
    }

    private void printResult(InputStream inputStream) throws IOException {
        println("\n结果：\n");
        System.out.println(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")).readLine());
        inputStream.close();
    }

    private void println(String message) {
        System.out.println(message);
    }


}
