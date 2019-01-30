package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.model.kdou.response.event.GameStartEvent;
import com.lelefans.mwy.model.kdou.response.event.SimpleGamer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 游戏房间容器，包括所有的房间和待匹配的房间
 */
public class RoomContainer {
    private static RoomContainer roomContainer = new RoomContainer();
    /**
     * 游戏房间集合
     */
    private Map<Integer, GameRoom> roomMap = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<Gamer> gameQueue = new ConcurrentLinkedQueue();


    private RoomContainer() {
    }

    public static RoomContainer getInstance() {
        return roomContainer;
    }

    /**
     * 添加房间
     *
     * @param room
     */
    public void putRoom(GameRoom room) {
        roomMap.put(room.getId(), room);
    }

    /**
     * 获取房间
     *
     * @param roomId
     * @return
     */
    public GameRoom getRoom(Integer roomId) {
        return roomMap.get(roomId);
    }

    /**
     * 移除房间
     *
     * @param roomId
     */
    public GameRoom removeRoom(Integer roomId) {
        return roomMap.remove(roomId);
    }

    /**
     * 匹配游戏玩家
     *
     * @return
     */
    public Gamer matchGamer() {
        return gameQueue.poll();
    }

    /**
     * 添加到待匹配的队列
     *
     * @param gamer
     */
    public void addToMatchQueue(Gamer gamer) {
        this.gameQueue.offer(gamer);
    }

    /**
     * 结束等待
     *
     * @param gamer
     */
    public void stopMatch(Gamer gamer) {
        gameQueue.remove(gamer);
    }

    /**
     * @param room
     */
    public void removeFromQueue(GameRoom room) {
        if (room == null) {
            return;
        }
        this.removeRoom(room.getId());
        if(!CollectionUtils.isEmpty(room.getGamers())){
            room.getGamers().forEach(e->e.setGameRoom(null));
        }
        this.gameQueue.remove(room);
    }

    /**
     * 刷新房间状态，包括房间是否有效，推送房间状态到客户端
     */
    public void flushStatus() {
        roomMap.values().stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getStatus() == GameRoomStatus.Gaming)
                .forEach(e -> e.flush());
        List<Integer> expiredRoomIds = roomMap.entrySet().stream().filter(Objects::nonNull)
                .filter(e -> {
                    return e == null || e.getValue() == null || e.getValue().getStatus() == GameRoomStatus.Game_Over;
                })
                .map(e -> e.getKey()).collect(Collectors.toList());
        expiredRoomIds.forEach(e -> roomMap.remove(e));
    }

}
