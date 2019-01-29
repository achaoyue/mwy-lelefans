package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.model.kdou.response.event.GameStartEvent;
import com.lelefans.mwy.model.kdou.response.event.SimpleGamer;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class GameRoom {
    private int id;
    private String name;
    private long createTime;
    private GameRoomStatus status;
    private List<Gamer> gamers;
    private List<Bomb> bombs = new ArrayList<>();

    public void flush() {
        WebSocketResponseMessageModel responseMessageModel = new WebSocketResponseMessageModel();
        responseMessageModel.setMessageType(ResponseMessageTypeEnum.Room_Status);
        GameStartEvent event = new GameStartEvent();

        if (!CollectionUtils.isEmpty(bombs)) {
            event.setBombs(bombs.stream().map(e -> buildBomb(e)).collect(Collectors.toList()));
        }

        if (!CollectionUtils.isEmpty(gamers)) {
            List<SimpleGamer> gamers = getGamers().stream().map(e -> getSimpleGamer(e)).collect(Collectors.toList());
            event.setGamers(gamers);
        }
        responseMessageModel.setData(event);

        // TODO: 2019-01-29 检查碰撞
        testHint();

        Optional.ofNullable(gamers).orElse(Collections.emptyList()).stream().forEach(e -> e.writeTextMessage(responseMessageModel));
    }

    private void testHint() {
        Optional.ofNullable(gamers).orElse(Collections.emptyList()).stream()
                .forEach(e->{
                    //子弹碰撞检查
                    
                    //礼品检查
                });
    }

    private SimpleGamer getSimpleGamer(Gamer e) {
        SimpleGamer gamer = new SimpleGamer();
        e.setX(e.getX() + Math.cos(e.getDirAngle()) * e.getSpeed());
        e.setY(e.getY() + Math.sin(e.getDirAngle()) * e.getSpeed());
        gamer.setX(e.getX());
        gamer.setY(e.getY());
        gamer.setId(e.getGamerId());
        return gamer;
    }

    private Point buildBomb(Bomb e) {
        Point p = new Point();
        e.setX(e.getX() + Math.cos(e.getDirAngle()) * e.getSpeed());
        e.setY(e.getY() + Math.sin(e.getDirAngle()) * e.getSpeed());
        p.setX(e.getX());
        p.setY(e.getY());
        return p;
    }

}
