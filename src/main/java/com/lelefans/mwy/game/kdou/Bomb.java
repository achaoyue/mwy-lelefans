package com.lelefans.mwy.game.kdou;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Bomb extends Point{
    /**
     * 游戏方向
     */
    private double dirAngle;
    /**
     * 子弹速度
     */
    private double speed;
    /**
     * 发射子弹的玩家id
     */
    private int gamerId;

    /**
     * 是否需要移除
     */
    private boolean needRemove = false;
}
