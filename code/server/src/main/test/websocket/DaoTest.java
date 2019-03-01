package websocket;


import com.kizen.eqiba.server.dao.mysql.SqlUserDao;
import com.kizen.eqiba.server.dao.redis.RedisUserDao;
import com.kizen.eqiba.server.entity.User;
import com.kizen.eqiba.server.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.Date;

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
//        redisTest();
//        sqlTest();
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
