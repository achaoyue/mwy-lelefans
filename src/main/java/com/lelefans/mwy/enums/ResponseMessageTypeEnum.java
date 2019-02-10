package com.lelefans.mwy.enums;

import lombok.Getter;

/**
 * 消息类型枚举
 */
@Getter
public enum ResponseMessageTypeEnum implements CodeEnum {
    Room_Status(1, "房间状态消息"),
    Game_Config(2, "有戏配置消息"),
    LOGIN(3, "登陆消息"),
    ENTER_ROOM(4,"进入房间"),
    CREATE_ROOM(5,"创建房间成功"),
    START_GAME(6,"开始游戏"),
    GAME_OVER(7,"游戏结束")
    ;

    int code;
    String desc;

    ResponseMessageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
