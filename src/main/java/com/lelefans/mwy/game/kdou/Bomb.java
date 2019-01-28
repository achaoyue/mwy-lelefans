package com.lelefans.mwy.game.kdou;

import lombok.Data;

@Data
public class Bomb {
    /**
     * 子弹x坐标
     */
    private int x;
    /**
     * 子弹y坐标
     */
    private int y;
    /**
     * 游戏方向
     */
    private int dirAngle;
    /**
     * 子弹速度
     */
    private int speed;
}
