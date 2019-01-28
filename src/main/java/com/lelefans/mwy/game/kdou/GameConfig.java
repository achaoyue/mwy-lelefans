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
}
