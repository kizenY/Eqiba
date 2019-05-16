package com.kizen.eqiba.server.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
    public int id;
    public String username;
    public String email;
    public String phoneNumber;
    public String password;
    public Date birthday;
    public String sex;
    public Timestamp createTime;
    public Timestamp loginTime;
    public Timestamp logoutTime;
    public String sessionId;


    @Override
    public String toString() {
        return "{\"User\":{"
                + "\"id\":"
                + id
                + ",\"username\":\""
                + username + '\"'
                + ",\"email\":\""
                + email + '\"'
                + ",\"phoneNumber\":\""
                + phoneNumber + '\"'
                + ",\"password\":\""
                + password + '\"'
                + ",\"birthday\":\""
                + birthday + '\"'
                + ",\"sex\":\""
                + sex + '\"'
                + ",\"createTime\":\""
                + createTime + '\"'
                + ",\"loginTime\":\""
                + loginTime + '\"'
                + ",\"logoutTime\":\""
                + logoutTime + '\"'
                + ",\"sessionId\":\""
                + sessionId + '\"'
                + "}}";

    }
}
