package com.lelefans.mwy.enums;

import lombok.Getter;

/**
 * 消息类型枚举
 */
@Getter
public enum RequestMessageTypeEnum implements CodeEnum {
    Create_Room(1,"创建房间"),
    Enter_Room(2,"进入房间"),
    Game_Start(3,"开始游戏"),
    Game_Option(4,"游戏操作消息"),
    Game_Rand_Match(5,"随机匹配"),
    Move(6,"移动消息"),
    Fire(7,"发射消息"),
    STOP_WAIT(8,"停止等待"),
    Play_With_SYS(9,"选择系统")
    ;

    int code;
    String desc;

    RequestMessageTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
