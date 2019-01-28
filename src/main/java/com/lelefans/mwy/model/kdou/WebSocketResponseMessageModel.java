package com.lelefans.mwy.model.kdou;

import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketResponseMessageModel {
    /**
     * 消息类型
     */
    private ResponseMessageTypeEnum messageType;

    private String msg;
}
