package com.lelefans.mwy.exceptions;

import com.lelefans.mwy.enums.ExceptionEnum;

public class GameException extends RuntimeException {
    private int code;

    public GameException(String msg,Throwable e){
        super(msg,e);
    }
    public GameException(){
        super();
    }
    public GameException(int code,Throwable e){
        super(e);
        this.code = code;
    }
    public GameException(int code){
        super();
        this.code = code;
    }
    public GameException(int code,String msg,Throwable e){
        super(msg,e);
        this.code = code;
    }
    public GameException(int code,String msg){
        super(msg);
        this.code = code;
    }
    public GameException(ExceptionEnum exception){
        super(exception.getDesc());
        this.code = exception.getCode();
    }
}
