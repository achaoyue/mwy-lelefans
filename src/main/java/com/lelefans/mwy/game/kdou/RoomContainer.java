package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

/**
 * 游戏房间容器，包括所有的房间和待匹配的房间
 */
public class RoomContainer {
    private static  RoomContainer roomContainer = new RoomContainer();
    /**
     * 游戏房间集合
     */
    private Map<Integer,GameRoom> roomMap = new ConcurrentHashMap<>();
    private Queue<GameRoom> gameQueue = new ConcurrentLinkedDeque<>();


    private RoomContainer(){}
    public static RoomContainer getInstance(){
        return roomContainer;
    }

    /**
     * 添加房间
     * @param room
     */
    public void putRoom(GameRoom room){
        roomMap.put(room.getId(),room);
    }

    /**
     * 获取房间
     * @param roomId
     * @return
     */
    public GameRoom getRoom(Integer roomId){
        return roomMap.get(roomId);
    }

    /**
     * 移除房间
     * @param roomId
     */
    public GameRoom removeRoom(Integer roomId){
        return roomMap.remove(roomId);
    }

    /**
     * 匹配游戏房间
     * @return
     */
    public GameRoom matchRoom(){
        return gameQueue.poll();
    }

    /**
     * 添加到待匹配的队列
     * @param room
     */
    public void addToMatchQueue(GameRoom room){
        this.gameQueue.offer(room);
    }

    public void removeFormQueue(GameRoom room){
        if(room == null){
            return;
        }
        this.removeRoom(room.getId());
        this.gameQueue.remove(room);
    }

    /**
     * 刷新房间状态，包括房间是否有效，推送房间状态到客户端
     */
    public void flushStatus(){
        roomMap.values().stream()
                .filter(Objects::nonNull)
                .filter(e->e.getStatus() == GameRoomStatus.Gaming)
                .forEach(e->{

                });
        List<Integer> expiredRoomIds = roomMap.entrySet().stream().filter(Objects::nonNull)
                .filter(e -> {
                    return e == null || e.getValue() == null || e.getValue().getStatus() == GameRoomStatus.Game_Over;
                })
                .map(e -> e.getKey()).collect(Collectors.toList());
        expiredRoomIds.forEach(e->roomMap.remove(e));
    }

}
