package com.lelefans.mwy.model.kdou.request.event;

import com.lelefans.mwy.game.kdou.Gamer;
import lombok.Data;

@Data
public class MoveChangeEvent {
    /**
     * 游戏玩家
     */
    private Gamer gamer;

    /**
     * 移动的角度
     */
    private double dirAngel;
    /**
     * 移动的速度
     */
    private double speed;
}
