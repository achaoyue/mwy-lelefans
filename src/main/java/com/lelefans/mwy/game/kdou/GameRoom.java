package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;
import lombok.Data;

import java.util.List;

@Data
public class GameRoom {
    private int id;
    private String name;
    private long createTime;
    private GameRoomStatus status;
    private List<Gamer> gamers;

}
