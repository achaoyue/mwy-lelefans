package com.lelefans.mwy.game.kdou;

import lombok.Data;

@Data
public class Bomb extends Point{
    /**
     * 游戏方向
     */
    private int dirAngle;
    /**
     * 子弹速度
     */
    private int speed;
    /**
     * 发射子弹的玩家id
     */
    private int gamerId;
}
