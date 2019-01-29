package com.lelefans.mwy;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.enums.GameRoomStatus;
import com.lelefans.mwy.game.kdou.Point;

import java.util.HashMap;
import java.util.Map;

public class TestPoint {
    public static void main(String[] args) {
        Point point = new Point();
        point.setX(10.123);
        point.setY(11.543);
        Map<String,Object> map = new HashMap<>();
        map.put("aaa", GameRoomStatus.Gaming);

        String s = JSON.toJSONString(point);
        System.out.println(s);

        String s1 = JSON.toJSONString(map);
        System.out.println(s1);
    }
}
