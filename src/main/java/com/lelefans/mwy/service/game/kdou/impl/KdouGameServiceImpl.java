package com.lelefans.mwy.service.game.kdou.impl;

import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.game.kdou.*;
import com.lelefans.mwy.model.kdou.request.event.*;
import com.lelefans.mwy.service.game.kdou.KdouGameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class KdouGameServiceImpl implements KdouGameService {
    @Resource
    private GameConfig gameConfig;
    @Resource
    private SystemMachine systemMachine;

    @Override
    public void enterRoom(EnterRoomEvent enterRoomEvent) {
        GameRoom room = RoomContainer.getInstance().getRoom(enterRoomEvent.getRoomId());
        if (room == null) {
            throw new GameException(ExceptionEnum.NO_ROOM, "房间id：" + enterRoomEvent.getRoomId());
        }
        if (room.getStatus() == GameRoomStatus.Gaming || room.getStatus() == GameRoomStatus.Game_Over) {
            throw new GameException(ExceptionEnum.CAN_NOT_ENTER_ROOM, room.getStatus().getDesc());
        }
        enterRoomEvent.getGamer().setGameRoom(room);
        room.getGamers().add(enterRoomEvent.getGamer());
    }

    @Override
    public void createRoom(CreateRoomEvent createRoomEvent) {

    }

    @Override
    public void randomMatch(RandomMatchEvent matchEvent) {
        RoomContainer roomContainer = RoomContainer.getInstance();
        Gamer gamer = roomContainer.matchGamer();
        if (gamer == null) {
            roomContainer.addToMatchQueue(matchEvent.getGamer());
        } else {
            GameRoom gameRoom = buildRoom(Arrays.asList(gamer, matchEvent.getGamer()));
            roomContainer.putRoom(gameRoom);
            // TODO: 2019-01-29 房间组件成功消息
        }
    }


    @Override
    public void playWithSystem(Gamer gamer) {
        RoomContainer.getInstance().stopMatch(gamer);
        Gamer sysGamer = new SysGamer();
        sysGamer.setGamerId(-1);
        sysGamer.setNickName("sys");
        systemMachine.AddGamer(sysGamer);
        GameRoom gameRoom = buildRoom(Arrays.asList(gamer, sysGamer));
        RoomContainer.getInstance().putRoom(gameRoom);
        // TODO: 2019-01-29 房间组件成功消息
    }

    @Override
    public void stopWaitMatch(StopWaitEvent stopWaitEvent) {
        RoomContainer.getInstance().stopMatch(stopWaitEvent.getGamer());
    }

    @Override
    public void changeMoveStatus(MoveChangeEvent moveChangeEvent) {
        Gamer gamer = moveChangeEvent.getGamer();
        gamer.setDirAngle(moveChangeEvent.getDirAngel());
        gamer.setSpeed(moveChangeEvent.getSpeed());
    }

    @Override
    public void fire(FireEvent fireEvent) {
        Gamer gamer = fireEvent.getGamer();
        if(gamer.getGameRoom() == null || gamer.getGameRoom().getStatus() != GameRoomStatus.Gaming){
            return;
        }
        long now = System.currentTimeMillis();
        if (now - gamer.getLastFireTime() < gameConfig.getFireInterval()) {
            return;
        } else {
            Bomb bomb = new Bomb();
            bomb.setX(gamer.getX());
            bomb.setY(gamer.getY());
            bomb.setDirAngle(gamer.getDirAngle());
            bomb.setSpeed(gameConfig.getBombSpeed() + gamer.getSpeed());
            bomb.setGamerId(gamer.getGamerId());
            gamer.getGameRoom().getBombs().add(bomb);
            gamer.setLastFireTime(now);
        }
    }

    private GameRoom buildRoom(List<Gamer> gamerList) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setGamers(gamerList);
        gameRoom.setId((int) (Math.random() * 1000000));
        gameRoom.setCreateTime(System.currentTimeMillis());
        gameRoom.setStatus(GameRoomStatus.Gaming);
        for (Gamer gamer : gamerList) {
            gamer.setGameRoom(gameRoom);
        }
        return gameRoom;
    }
}
