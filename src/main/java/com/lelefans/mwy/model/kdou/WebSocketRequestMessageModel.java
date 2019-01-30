package com.lelefans.mwy.model.kdou;

import com.alibaba.fastjson.annotation.JSONField;
import com.lelefans.mwy.enums.RequestMessageTypeEnum;
import com.lelefans.mwy.game.kdou.Gamer;
import com.lelefans.mwy.util.SimpleSerializer;
import lombok.Data;

@Data
public class WebSocketRequestMessageModel {
    @JSONField(deserializeUsing = SimpleSerializer.class)
    private RequestMessageTypeEnum messageType;
    private Gamer gamer;
    private Object data;
}
