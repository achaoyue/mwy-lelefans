package com.lelefans.mwy.enums;

import lombok.Getter;

/**
 * 消息类型枚举
 */
@Getter
public enum ResponseMessageTypeEnum implements CodeEnum {
    Room_Status(1,"房间状态消息"),
    Game_Config(2,"有戏配置消息")
    ;

    int code;
    String desc;

    ResponseMessageTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
