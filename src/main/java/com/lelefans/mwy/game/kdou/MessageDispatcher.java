package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;

/**
 * 消息分派器
 */
public class MessageDispatcher {
    private static MessageDispatcher dispatcher = new MessageDispatcher();
    private MessageDispatcher(){}
    public static MessageDispatcher getInstance(){
        return dispatcher;
    }

    /**
     * 派送消息
     * @param requestMessage
     */
    public void dispatchMessage(WebSocketRequestMessageModel requestMessage){
        switch (requestMessage.getMessageType()){
            case Create_Room:{
                break;
            }
            case Enter_Room:{
                break;
            }
            case Game_Start:{
                break;
            }
            case Game_Option:{
                break;
            }
            default:{
                throw new GameException(ExceptionEnum.NO_Msg_Handler);
            }
        }
    }
}
