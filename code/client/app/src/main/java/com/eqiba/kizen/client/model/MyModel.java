package com.eqiba.kizen.client.model;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public interface MyModel {

    OkHttpClient okHttpClient = new OkHttpClient();

    String URL_HOME = "10.42.0.1:8080/server_war/";

    String PROTOCOL_HTTP = "http://";

    MediaType JSON = MediaType.get("application/json; charset=utf-8");

    //响应内容遗失异常
    class NullBodyException extends Exception{}

    //数据库存取异常
    class DatabaseException extends Exception{}


}
