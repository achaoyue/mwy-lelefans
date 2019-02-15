package com.lelefans.mwy.game.kdou;

import lombok.Data;

@Data
public class Gift extends Point{

    /**
     * 礼物成熟时间
     */
    private long time;
    /**
     * 礼物得分
     */
    private int money;
    /**
     * 无效
     */
    private boolean remove = false;
}
