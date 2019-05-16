package websocket;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizen.eqiba.server.dao.mysql.SqlUserDao;
import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.entity.dto.EqibaResult;
import com.kizen.eqiba.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = ("classpath:spring/spring-*.xml"))
public class DaoTest {
    @Autowired
    private RedisUserDao redisUserDao;
    @Autowired
    private SqlUserDao sqlUserDao;


    @Test
    public void test()
    {
        User user = new User();
        user.id=2;
        List<User> users = new ArrayList<>(1);
        users.add(user);
        EqibaResult<User> result = new EqibaResult<>();
        result.data=users;

        ObjectMapper mapper = new ObjectMapper();
        EqibaResult<User> get = null;
        try {
            get = mapper.readValue(result.toString(), new TypeReference<EqibaResult<User>>(){

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(get.data.get(0).id);
    }

    private void redisTest()
    {

        User user = new User();
        user.id=2;
        redisUserDao.add("aaa",user);

//        System.out.println(redisUserDao.exist("bbb"));
//        redisUserDao.transferToBreakTable("bbb");
//        System.out.println(redisUserDao.exist(user));
//        redisUserDao.existAndUpdate("bbb");
//        redisUserDao.remove("bbb");
    }

    private void sqlTest()
    {
//        User user = new User();
//        user.username="bbb";
//        user.password="123456";
//        user.createTime=new Timestamp(new Date().getTime());
//        sqlUserDao.createUser(user);
        User user=sqlUserDao.getUserByAccount("bbb");
        System.out.println(user.password);
    }
}
