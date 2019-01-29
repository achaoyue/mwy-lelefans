package com.lelefans.mwy.socket;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.enums.ResponseMessageTypeEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.game.kdou.Gamer;
import com.lelefans.mwy.game.kdou.MessageDispatcher;
import com.lelefans.mwy.game.kdou.RoomContainer;
import com.lelefans.mwy.model.UserModel;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;
import com.lelefans.mwy.model.kdou.WebSocketResponseMessageModel;
import com.lelefans.mwy.service.game.kdou.LoginService;
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

@ServerEndpoint(prefix = "netty-websocket")
@Component
@Slf4j
public class KDouWebSocketPoint extends Gamer implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private LoginService loginService;
    private MessageDispatcher messageDispatcher;
    public KDouWebSocketPoint(){
        if(applicationContext == null){
            return;
        }
        this.loginService = applicationContext.getBean(LoginService.class);
        this.messageDispatcher = applicationContext.getBean(MessageDispatcher.class);
    }


    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        System.out.println("connect");
        this.setSession(session);
        String token = parameterMap.getParameter("token");
        UserModel login = loginService.login(token);
        if(login == null){
            writeTextMessage(WebSocketResponseMessageModel.builder().messageType(ResponseMessageTypeEnum.LOGIN).data(false).build());
            session.close();
        }
        this.setGamerId(login.getId());
        this.setNickName(login.getName());
    }

    @OnClose
    public void onClose(Session session) {
        RoomContainer.getInstance().removeFromQueue(this.getGameRoom());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        WebSocketRequestMessageModel requestMessageModel = null;
        try {
            requestMessageModel = JSON.parseObject(message, WebSocketRequestMessageModel.class);
            requestMessageModel.setGamer(this);
        } catch (Exception e) {
            throw new GameException(ExceptionEnum.Msg_Form_Error,e);
        }
        messageDispatcher.dispatchMessage(requestMessageModel);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        RoomContainer.getInstance().removeFromQueue(this.getGameRoom());
        log.error("websocket 错误,name:{},id:{}", this.getNickName(), this.getGamerId(), error);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
