package com.lelefans.mwy.game.kdou;

import lombok.Data;

@Data
public class Gift {
    /**
     * 坐标x
     */
    private int x;
    /**
     * 坐标y
     */
    private int y;
    /**
     * 礼物成熟时间
     */
    private long time;
    /**
     * 礼物得分
     */
    private int money;
}
