package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.model.kdou.response.event.GameStartEvent;
import com.lelefans.mwy.model.kdou.response.event.SimpleBomb;
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
    private List<Gift> gifts = new ArrayList<>();

    public void flush() {
        if(isGameOver()){
            status = GameRoomStatus.Game_Over;
           return;
        }
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
        bombs.removeAll(bombs.stream().filter(e -> e.isNeedRemove()).collect(Collectors.toList()));

        testHint();

        dispatchMsg(responseMessageModel);
    }

    private void testHint() {
        GameConfig gameConfig = GameConfig.getInstance();
        Optional.ofNullable(gamers).orElse(Collections.emptyList()).stream()
                .forEach(e -> {
                    //子弹碰撞检查
                    for (Bomb bomb : bombs) {
                        double x = bomb.getX() - e.getX();
                        double y = bomb.getY() - e.getY();
                        if (bomb.getGamerId() != e.getGamerId() && x * x + y * y < 50 * 50) {
                            bomb.setNeedRemove(true);
                            e.setScore(e.getScore() - gameConfig.getHintDesc());
                        }
                    }
                    //礼品检查
                    for (Gift gift : gifts) {
                        double x = gift.getX() - e.getX();
                        double y = gift.getY() - e.getY();
                        if (x * x + y * y < 60 * 60 && gift.getTime() < System.currentTimeMillis()) {
                            e.setScore(e.getScore() + gift.getMoney());
                        }
                    }
                });
    }

    private SimpleGamer getSimpleGamer(Gamer e) {
        GameConfig gameConfig = GameConfig.getInstance();

        SimpleGamer gamer = new SimpleGamer();
        double x = e.getX() + Math.cos(e.getDirAngle()) * e.getSpeed();
        x = x < 0 ? 0 : (x > gameConfig.getWidth() ? gameConfig.getWidth() : x);
        double y = e.getY() + Math.sin(e.getDirAngle()) * e.getSpeed();
        y = y < 0 ? 0 : (y > gameConfig.getHeight() ? gameConfig.getHeight() : y);
        if (testAvailable((int) x, (int) y)) {
            e.setX(x);
            e.setY(y);
        }
        gamer.setX(e.getX());
        gamer.setY(e.getY());
        gamer.setId(e.getGamerId());
        gamer.setScore(e.getScore());
        return gamer;
    }

    private Point buildBomb(Bomb e) {
        GameConfig gameConfig = GameConfig.getInstance();
        SimpleBomb p = new SimpleBomb();
        e.setX(e.getX() + Math.cos(e.getDirAngle()) * e.getSpeed());
        e.setY(e.getY() + Math.sin(e.getDirAngle()) * e.getSpeed());
        p.setX(e.getX());
        p.setY(e.getY());
        e.setNeedRemove((e.getX() < 0
                || e.getX() > gameConfig.getWidth()
                || e.getY() < 0
                || e.getY() > gameConfig.getHeight()
                || e.isNeedRemove() == true
                || !testAvailable((int) p.getX(), (int) p.getY())));
        p.setRemove(e.isNeedRemove()==true?true:null);
        return p;
    }

    public void destroy() {
        if (gamers != null) {
            gamers.forEach(e -> e.setGameRoom(null));
            gamers = null;
        }
        bombs.clear();
        gifts.clear();
        status = GameRoomStatus.Game_Over;
    }

    public void dispatchMsg(WebSocketResponseMessageModel responseMessageModel) {
        Optional.ofNullable(gamers).orElse(Collections.emptyList()).stream().forEach(e -> e.writeTextMessage(responseMessageModel));
    }

    public boolean testAvailable(int x, int y) {
        GameConfig gameConfig = GameConfig.getInstance();
        int data = (gameConfig.getWidth() / 10) * (y / 10) + x / 10;
        int w = data / 32;
        int h = data % 32;
        if(data<0 || data/32>gameConfig.getMap().length){
            return false;
        }
        return (gameConfig.getMap()[w] >>> (31 - h) & 1) == 0;
    }
    public boolean isGameOver(){
        if (!CollectionUtils.isEmpty(gamers)) {
            for (Gamer gamer : this.gamers) {
                if (gamer.getScore() <= 0 ){
                    WebSocketResponseMessageModel responseMessageModel = new WebSocketResponseMessageModel();
                    responseMessageModel.setMessageType(ResponseMessageTypeEnum.GAME_OVER);
                    responseMessageModel.setData(gamer.getGamerId());
                    dispatchMsg(responseMessageModel);
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
