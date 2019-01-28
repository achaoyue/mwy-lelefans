package com.lelefans.mwy.enums;

import lombok.Getter;

@Getter
public enum GameRoomStatus implements CodeEnum {
    Wait_Start(0,"待开始"),
    Gaming(1,"进行中"),
    Game_Over(2,"游戏结束")
    ;

    int code;
    String desc;

    GameRoomStatus(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
}