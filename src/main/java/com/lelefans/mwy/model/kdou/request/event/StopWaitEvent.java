package com.lelefans.mwy.model.kdou.request.event;

import com.lelefans.mwy.game.kdou.Gamer;
import lombok.Data;

@Data
public class StopWaitEvent {
    private Gamer gamer;
}