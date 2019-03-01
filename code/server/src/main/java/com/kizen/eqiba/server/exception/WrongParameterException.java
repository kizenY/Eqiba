package com.kizen.eqiba.server.exception;

public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String msg)
    {
        super(msg);
    }
}
