package com.lelefans.mwy.model.kdou;

import com.lelefans.mwy.enums.RequestMessageTypeEnum;
import com.lelefans.mwy.game.kdou.Gamer;
import lombok.Data;

@Data
public class WebSocketRequestMessageModel {
    private RequestMessageTypeEnum messageType;
    private Gamer gamer;
    private Object data;
}
