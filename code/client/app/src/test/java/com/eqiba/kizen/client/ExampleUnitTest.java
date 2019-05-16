package com.eqiba.kizen.client;

import android.support.annotation.NonNull;
import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.MyModel;
import com.eqiba.kizen.client.model.UserModel;
import com.eqiba.kizen.client.model.impl.UserModelImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.*;
import org.junit.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testRxJava() throws IOException, MyModel.NullBodyException {
        UserModel userModel = new UserModelImpl();
        User user = new User();
        user.username="test";
        user.password="test";

        System.out.println(userModel.register(user.username,user.password).toString());

        System.out.println(userModel.login(user.username,user.password).toString());

        System.out.println(userModel.logout(UserModelImpl.sessionId).toString());

    }

    @Test
    public void testJson()
    {
//        List<User> users = new ArrayList<>(1);
//        User user = new User();
//        user.id=5;
//        users.add(user);
        EqibaResult respons = new EqibaResult<>("110","成功",null);
        String result = respons.toString();
        System.out.println(result);
        EqibaResult get = EqibaResult.getResultFromJsonString(result);
        System.out.println(get.message);
    }
}