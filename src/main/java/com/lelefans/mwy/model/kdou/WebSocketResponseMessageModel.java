package com.lelefans.mwy.model.kdou;

import com.alibaba.fastjson.annotation.JSONField;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.util.SimpleSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketResponseMessageModel {
    public static final WebSocketResponseMessageModel loginSuccess
            = WebSocketResponseMessageModel.builder().messageType(ResponseMessageTypeEnum.LOGIN).data(true).build();

    /**
     * 消息类型
     */
    @JSONField(serializeUsing = SimpleSerializer.class)
    private ResponseMessageTypeEnum messageType;
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 消息时间
     */
    private long stamp = System.currentTimeMillis();
}
