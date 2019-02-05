package com.lelefans.mwy.game.kdou;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GameConfig {
    private static GameConfig instance = null;
    public static GameConfig getInstance(){
        return instance;
    }
    public GameConfig(){
        this.instance = this;
    }

    /**
     * 刷新频率，默认每秒钟刷新20次
     */
    private int flushFrequency = 20;
    /**
     * 子弹速度
     */
    private int bombSpeed = 8;
    /**
     * 子弹发射时间间隔，1000表示每秒只能发射一个子弹
     */
    private int fireInterval = 1000;
    /**
     * 游戏界面宽高
     */
    private int width = 640;
    private int height = 1136;
    /**
     * 击中一次减去分数
     */
    private int hintDesc = 5;
}
