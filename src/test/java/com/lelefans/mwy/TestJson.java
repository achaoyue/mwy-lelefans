package com.lelefans.mwy;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;

public class TestJson {
    public static void main(String[] args) {
        String json = "{\"messageType\":1,\"data\":{\"dirAngel\":2,\"speed\":3}}";
        WebSocketRequestMessageModel messageModel = JSON.parseObject(json, WebSocketRequestMessageModel.class);
        System.out.println(messageModel);
    }
}
