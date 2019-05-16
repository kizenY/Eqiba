package com.kizen.eqiba.server.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizen.eqiba.server.entity.User;

import java.io.IOException;
import java.util.List;

/**
 * 服务器向客户端返回结果的格式类
 * @param <T> T为携带的数据类型
 */
public class EqibaResult<T> {
    public static ObjectMapper mapper = new ObjectMapper();
    /**
     * 结果代码（000-999）：
     * 分为三位：第一位表示模块，第二位表操作，第三位表结果，其中第三位为0表示成功
     */
   public String code;
    /**
     * 返回结果的详细信息
     */
   public String message;
    /**
     * 返回数据
     */
   public List<T> data;

    public EqibaResult() {
    }

    public EqibaResult(String code, String message, List<T> data)
   {
       this(code,message);
       this.data =data;
   }

   public EqibaResult(String code,String message)
   {
       this.code =code;
       this.message =message;
   }
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EqibaResult getResultFromJsonString(String json)
    {
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return mapper.readValue(json,EqibaResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
