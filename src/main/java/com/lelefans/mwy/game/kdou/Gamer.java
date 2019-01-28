package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.exceptions.GameException;
import lombok.Data;
import org.yeauty.pojo.Session;

/**
 * 游戏玩家
 */
@Data
public class Gamer {
    Session session;
    /**
     * 玩家id
     */
    private long gamerId;
    /**
     * 玩家昵称
     */
    private String nickName;
    /**
     * 游戏玩家坐标x
     */
    private int x;
    /**
     * 游戏玩家坐标y
     */
    private int y;
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
    private Integer dirAngle;
    /**
     * 玩家速度
     */
    private int speed;

    ////--------------方法区---------------
    public void enterRoom(Integer roomId){
        GameRoom room = RoomContainer.getInstance().getRoom(roomId);
        if(room == null){
            throw new GameException(ExceptionEnum.NO_ROOM.getCode(),"房间不存在");
        }
        this.gameRoom = room;
    }

}
