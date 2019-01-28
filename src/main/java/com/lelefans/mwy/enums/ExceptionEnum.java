package com.lelefans.mwy.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum implements CodeEnum {
    OTHER(0,"其它"),
    NO_ROOM(1,"房间不存在"),
    Msg_Form_Error(2,"消息格式错误"),
    NO_Msg_Handler(3,"未找到消息处理器")
    ;
    private int code;
    private String desc;
    ExceptionEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
