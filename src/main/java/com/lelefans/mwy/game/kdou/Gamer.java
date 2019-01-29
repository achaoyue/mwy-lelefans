package com.lelefans.mwy.game.kdou;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import lombok.Data;
import org.yeauty.pojo.Session;

/**
 * 游戏玩家
 */
@Data
public class Gamer extends Point{
    Session session;
    /**
     * 玩家id
     */
    private int gamerId;
    /**
     * 玩家昵称
     */
    private String nickName;
    /**
     * 游戏房间
     */
    private GameRoom gameRoom;
    /**
     * 玩家得分
     */
    private int score;
    /**
     * 玩家方向
     */
    private int dirAngle;
    /**
     * 玩家速度
     */
    private int speed;
    /**
     * 上次发射时间
     */
    private long lastFireTime;

    public void writeTextMessage(WebSocketResponseMessageModel responseModel) {
        this.getSession().sendText(JSON.toJSONString(responseModel));
    }
}
