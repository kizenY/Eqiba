package com.eqiba.kizen.client.model.impl;


import android.support.annotation.NonNull;
import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.DBHelper;
import com.eqiba.kizen.client.model.MyModel;
import com.eqiba.kizen.client.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

import static com.eqiba.kizen.client.model.DBHelper.TABLE_NAME_USER;
import static com.eqiba.kizen.client.model.DBHelper.getObjectFromDatabase;

public class UserModelImpl implements MyModel, UserModel {

    //登陆的用户
    public static User user = null;
    //用户在服务器的身份凭证
    public static String sessionId = null;

    @Override
    public EqibaResult register(String username, String password) throws IOException, NullBodyException {

        User registerUser = new User();
        registerUser.username = username;
        registerUser.password = password;

        Response response = okHttpClient.newCall(new Request.Builder()
                .url(URL_USER + "register")
                .post(RequestBody.create(JSON, registerUser.toString()))
                .build())
                .execute();

        return EqibaResult.getResultFromJsonString(response.body().string());
    }

    @Override
    public EqibaResult<User> login(String account, String password) throws IOException, NullBodyException {

        String jsonString = "{"
                + "\"account\" :"
                + "\"" + account + "\""
                + ",\"password\" :"
                + "\"" + password + "\"}";

        Response response = okHttpClient.newCall(
                new Request.Builder()
                        .url(URL_USER + "login")
                        .post(RequestBody.create(JSON, jsonString))
                        .build()
        ).execute();

        String value = response.header("Set-Cookie");
        sessionId = value.substring(0, value.indexOf(";"));

        return EqibaResult.getResultFromJsonString(response.body().string(), new TypeReference<EqibaResult<User>>() {
        });
    }

    @Override
    public EqibaResult logout(String sessionId) throws IOException, NullBodyException {

        Response response = okHttpClient.newCall(
                new Request.Builder()
                        .url(URL_USER + "needOnline/logout")
                        .addHeader("cookie", sessionId)
                        .build())
                .execute();
        return EqibaResult.getResultFromJsonString(response.body().string());
    }

    @Override
    public void addUser(User user) throws DatabaseException {

        String sql = "insert into " + TABLE_NAME_USER + "(data) values (?)";
        DBHelper.instance.getWritableDatabase().execSQL(sql, new Object[]{DBHelper.getDataFromObject(user)});

    }

    @Override
    public void deleteUser(User user) throws DatabaseException {
        DBHelper.instance.getWritableDatabase().delete(TABLE_NAME_USER, "data", new String[]{"" + user.id});
    }

    @Override
    public User getUser() throws DatabaseException {
        String sql = "select * from " + TABLE_NAME_USER;
        return (User) getObjectFromDatabase(sql, null).get(0);
    }


}
