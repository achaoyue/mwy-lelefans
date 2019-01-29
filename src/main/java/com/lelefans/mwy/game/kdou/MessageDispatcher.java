package com.lelefans.mwy.game.kdou;

import com.alibaba.fastjson.JSON;
import com.lelefans.mwy.enums.ExceptionEnum;
import com.lelefans.mwy.exceptions.GameException;
import com.lelefans.mwy.model.kdou.WebSocketRequestMessageModel;
import com.lelefans.mwy.model.kdou.request.event.FireEvent;
import com.lelefans.mwy.model.kdou.request.event.MoveChangeEvent;
import com.lelefans.mwy.model.kdou.request.event.RandomMatchEvent;
import com.lelefans.mwy.model.kdou.request.event.StopWaitEvent;
import com.lelefans.mwy.service.game.kdou.KdouGameService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息分派器
 */
@Component
public class MessageDispatcher {
    @Resource
    private KdouGameService gameService;
    /**
     * 派送消息
     * @param requestMessage
     */
    public void dispatchMessage(WebSocketRequestMessageModel requestMessage){
        switch (requestMessage.getMessageType()){
            case Create_Room:{
                break;
            }
            case Game_Rand_Match:{
                RandomMatchEvent event = new RandomMatchEvent();
                event.setGamer(requestMessage.getGamer());
                gameService.randomMatch(event);
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
            case STOP_WAIT:{
                StopWaitEvent event = new StopWaitEvent();
                event.setGamer(requestMessage.getGamer());
                gameService.stopWaitMatch(event);
                break;
            }
            case Play_With_SYS:{
                gameService.playWithSystem(requestMessage.getGamer());
                break;
            }
            case Move:{
                MoveChangeEvent moveChangeEvent = JSON.toJavaObject((JSON) requestMessage.getData(), MoveChangeEvent.class);
                moveChangeEvent.setGamer(requestMessage.getGamer());
                gameService.changeMoveStatus(moveChangeEvent);
                break;
            }
            case Fire:{
                FireEvent fireEvent = JSON.toJavaObject((JSON) requestMessage.getData(), FireEvent.class);
                fireEvent.setGamer(requestMessage.getGamer());
                gameService.fire(fireEvent);
                break;
            }
            default:{
                throw new GameException(ExceptionEnum.NO_Msg_Handler);
            }
        }
    }
}
