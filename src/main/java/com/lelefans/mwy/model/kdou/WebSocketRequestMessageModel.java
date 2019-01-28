package com.lelefans.mwy.model.kdou;

import com.lelefans.mwy.enums.RequestMessageTypeEnum;
import lombok.Data;

@Data
public class WebSocketRequestMessageModel {
    private RequestMessageTypeEnum messageType;
    private Object data;
}
