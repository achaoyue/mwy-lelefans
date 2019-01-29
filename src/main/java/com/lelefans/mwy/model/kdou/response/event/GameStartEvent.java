package com.lelefans.mwy.model.kdou.response.event;

import com.lelefans.mwy.game.kdou.Point;
import lombok.Data;

import java.util.List;

@Data
public class GameStartEvent {
    List<SimpleGamer> gamers;
    List<Point> bombs;
}
