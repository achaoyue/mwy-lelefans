package com.lelefans.mwy.service.game.kdou;

import com.lelefans.mwy.game.kdou.Gamer;
import com.lelefans.mwy.model.kdou.request.event.*;

public interface KdouGameService {
    /**
     * 进入房间
     * @param enterRoomEvent
     */
    void enterRoom(EnterRoomEvent enterRoomEvent);

    /**
     * 创建房间
     * @param createRoomEvent
     */
    void createRoom(CreateRoomEvent createRoomEvent);

    /**
     * 随机匹配
     * @param matchEvent
     */
    void randomMatch(RandomMatchEvent matchEvent);

    /**
     * 游戏玩家和系统战斗
     * @param gamer
     */
    void playWithSystem(Gamer gamer);

    void stopWaitMatch(StopWaitEvent stopWaitEvent);
    /**
     * 移动方向事件
     * @param moveChangeEvent
     */
    void changeMoveStatus(MoveChangeEvent moveChangeEvent);

    /**
     * 发射子弹
     * @param fireEvent
     */
    void fire(FireEvent fireEvent);
}
