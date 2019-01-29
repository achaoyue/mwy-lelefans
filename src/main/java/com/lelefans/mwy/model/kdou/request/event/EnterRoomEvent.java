package com.lelefans.mwy.model.kdou.request.event;

import com.lelefans.mwy.game.kdou.Gamer;
import lombok.Data;

@Data
public class EnterRoomEvent {
    private Gamer gamer;
    private Integer roomId;
}
