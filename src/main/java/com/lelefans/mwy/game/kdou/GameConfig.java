package com.lelefans.mwy.game.kdou;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GameConfig {
    /**
     * 刷新频率，默认每秒钟刷新20次
     */
    private int flushFrequency = 20;
    /**
     * 子弹速度
     */
    private int bombSpeed = 5;
    /**
     * 子弹发射时间间隔，1000表示每秒只能发射一个子弹
     */
    private int fireInterval = 1000;
}
