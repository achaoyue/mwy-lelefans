package com.lelefans.mwy.exceptions;

import com.lelefans.mwy.enums.ExceptionEnum;

public class GameException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public GameException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getDesc());
        this.exceptionEnum = exceptionEnum;
    }
    public GameException(ExceptionEnum exceptionEnum,Throwable e){
        super(exceptionEnum.getDesc(),e);
        this.exceptionEnum = exceptionEnum;
    }
    public GameException(ExceptionEnum exceptionEnum,String msg,Throwable e){
        super(exceptionEnum.getDesc()+","+msg);
        this.exceptionEnum = exceptionEnum;
    }
    public GameException(ExceptionEnum exceptionEnum,String msg){
        super(exceptionEnum.getDesc()+","+msg);
        this.exceptionEnum = exceptionEnum;
    }
}
