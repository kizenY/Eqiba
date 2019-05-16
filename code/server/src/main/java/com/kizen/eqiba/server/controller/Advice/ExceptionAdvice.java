package com.kizen.eqiba.server.controller.Advice;

import com.kizen.eqiba.server.entity.dto.EqibaResult;
import com.kizen.eqiba.server.exception.DatabaseException;
import com.kizen.eqiba.server.exception.NullParamterExcpetion;
import com.kizen.eqiba.server.exception.WrongParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 统一处理异常，并将错误信息反馈给用户
 * code第一位为0
 */

@ControllerAdvice
@ResponseBody
public class ExceptionAdvice{

    private final String CODE_FIRST="0";

    @ResponseBody
    @ExceptionHandler(value = DatabaseException.class)
    public EqibaResult handleDatabaseException(DatabaseException e)
    {
        return new EqibaResult(CODE_FIRST+"01",e.getMessage()+"服务器数据库操作出现错误！请检查数据是否合规。",null);
    }

    @ResponseBody
    @ExceptionHandler(value = NullParamterExcpetion.class)
    public EqibaResult handleNullPointerException(NullParamterExcpetion e)
    {
        return new EqibaResult(CODE_FIRST+"02",e.getMessage()+"不能包含空的数据！请检查填写信息！",null);
    }

    @ResponseBody
    @ExceptionHandler(value = WrongParameterException.class)
    public EqibaResult handleWrongParameterException(WrongParameterException e)
    {
        return new EqibaResult(CODE_FIRST+"03",e.getMessage()+"请检查数据格式！",null);
    }
}
