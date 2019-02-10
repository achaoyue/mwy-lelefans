package com.lelefans.mwy.socket;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.game.kdou.GameRoom;
import com.lelefans.mwy.game.kdou.Gamer;
import com.lelefans.mwy.game.kdou.MessageDispatcher;
import com.lelefans.mwy.game.kdou.RoomContainer;
import com.lelefans.mwy.model.UserModel;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.service.game.kdou.KdouGameService;
import com.lelefans.mwy.service.game.kdou.LoginService;
import com.lelefans.mwy.service.game.kdou.impl.KdouGameServiceImpl;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@ServerEndpoint(prefix = "netty-websocket")
@Component
@Slf4j
public class KDouWebSocketPoint implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private LoginService loginService;
    private Gamer gamer;
    private MessageDispatcher messageDispatcher;
    private KdouGameService gameService;
    public KDouWebSocketPoint(){
        if(applicationContext == null){
            return;
        }
        this.loginService = applicationContext.getBean(LoginService.class);
        this.messageDispatcher = applicationContext.getBean(MessageDispatcher.class);
        this.gameService =  applicationContext.getBean(KdouGameService.class);
    }


    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        System.out.println("connect");
        this.gamer = new Gamer();
        this.gamer.setSession(session);
        String token = parameterMap.getParameter("token");
        UserModel login = loginService.login(token);
        if(login == null){
            this.gamer.writeTextMessage(WebSocketResponseMessageModel.builder().messageType(ResponseMessageTypeEnum.LOGIN).data(false).build());
            session.close();
        }
        this.gamer.setGamerId(login.getId());
        this.gamer.setNickName(login.getName());
        WebSocketResponseMessageModel messageModel = new WebSocketResponseMessageModel();
        messageModel.setMessageType(ResponseMessageTypeEnum.LOGIN);
        messageModel.setData(true);
        this.gamer.writeTextMessage(messageModel);
    }

    @OnClose
    public void onClose(Session session) {
        sendLeaveMsg();
        RoomContainer.getInstance().removeFromQueue(this.gamer);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        WebSocketRequestMessageModel requestMessageModel = null;
        try {
            requestMessageModel = JSON.parseObject(message, WebSocketRequestMessageModel.class);
            requestMessageModel.setGamer(this.gamer);
        } catch (Exception e) {
            throw new GameException(ExceptionEnum.Msg_Form_Error,e);
        }
        messageDispatcher.dispatchMessage(requestMessageModel);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        sendLeaveMsg();
        RoomContainer.getInstance().removeFromQueue(this.gamer);
        log.error("websocket 错误,name:{},id:{}", this.gamer.getNickName(), this.gamer.getGamerId(), error);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void sendLeaveMsg(){
        GameRoom gameRoom = gamer.getGameRoom();
        if(gameRoom == null){
            return;
        }
        WebSocketResponseMessageModel responseMessageModel = new WebSocketResponseMessageModel();
        responseMessageModel.setMessageType(ResponseMessageTypeEnum.GAME_OVER);
        responseMessageModel.setData("close");
        Optional.ofNullable(gameRoom.getGamers()).orElse(Collections.emptyList()).forEach(e->e.writeTextMessage(responseMessageModel));
    }
}
