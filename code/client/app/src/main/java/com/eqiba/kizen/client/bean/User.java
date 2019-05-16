package com.eqiba.kizen.client.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
