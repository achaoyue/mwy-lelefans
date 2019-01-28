package com.lelefans.mwy.socket;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.game.kdou.Gamer;
import com.lelefans.mwy.game.kdou.MessageDispatcher;
import com.lelefans.mwy.game.kdou.RoomContainer;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;

@ServerEndpoint(prefix = "netty-websocket")
@Component
@Slf4j
public class KDouWebSocketPoint extends Gamer {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) {
        System.out.println("connect");
        this.setSession(session);

        // TODO: 2019-01-27  玩家信息补充
    }

    @OnClose
    public void onClose(Session session) {
        RoomContainer.getInstance().removeFormQueue(this.getGameRoom());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        WebSocketRequestMessageModel requestMessageModel = null;
        try {
            requestMessageModel = JSON.parseObject(message, WebSocketRequestMessageModel.class);
        } catch (Exception e) {
            throw new GameException(ExceptionEnum.Msg_Form_Error.getCode(),e);
        }
        MessageDispatcher.getInstance().dispatchMessage(requestMessageModel);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        RoomContainer.getInstance().removeFormQueue(this.getGameRoom());
        log.error("websocket 错误,name:{},id:{}", this.getNickName(), this.getGamerId(), error);
    }

    public void writeMessage(WebSocketResponseMessageModel responseModel) throws IOException {
        this.getSession().sendText(JSON.toJSONString(responseModel));
    }
}
