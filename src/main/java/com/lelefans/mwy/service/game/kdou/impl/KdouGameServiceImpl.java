package com.lelefans.mwy.service.game.kdou.impl;

import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.game.kdou.*;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.model.kdou.request.event.*;
import com.lelefans.mwy.service.game.kdou.KdouGameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

            WebSocketResponseMessageModel messageModel = new WebSocketResponseMessageModel();
            messageModel.setMessageType(ResponseMessageTypeEnum.START_GAME);
            messageModel.setData(gameRoom.getGamers().stream().map(e->e.getGamerId()).collect(Collectors.toList()));
            gameRoom.dispatchMsg(messageModel);
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

        WebSocketResponseMessageModel messageModel = new WebSocketResponseMessageModel();
        messageModel.setMessageType(ResponseMessageTypeEnum.START_GAME);
        messageModel.setData(gameRoom.getGamers().stream().map(e->e.getGamerId()).collect(Collectors.toList()));
        gameRoom.dispatchMsg(messageModel);
    }

    @Override
    public void stopWaitMatch(StopWaitEvent stopWaitEvent) {
        RoomContainer.getInstance().stopMatch(stopWaitEvent.getGamer());
    }

    @Override
    public void changeMoveStatus(MoveChangeEvent moveChangeEvent) {
        Gamer gamer = moveChangeEvent.getGamer();
        if(moveChangeEvent.getSpeed() != 0){
            gamer.setDirAngle(moveChangeEvent.getDirAngel());
        }
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
            gamer.setScore(300);
            Point p = getAvailablePoint();
            while (!gameRoom.testAvailable((int)p.getX(),(int)p.getY())){
                p = getAvailablePoint();
            }
            gamer.setX(p.getX());
            gamer.setY(p.getY());
        }
        return gameRoom;
    }

    private Point getAvailablePoint(){
        Point p = new Point();
        p.setX(Math.random()* GameConfig.getInstance().getWidth());
        p.setY(Math.random()* GameConfig.getInstance().getHeight());
        return p;
    }
}
