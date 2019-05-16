package com.eqiba.kizen.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
       this.code =code;
       this.message =message;
       this.data =data;
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

    public static EqibaResult getResultFromJsonString(String json, TypeReference typeReference)
    {
        try {
            return mapper.readValue(json,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EqibaResult getResultFromJsonString(String json)
    {
        try {
            return mapper.readValue(json,new TypeReference<EqibaResult<Void>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
