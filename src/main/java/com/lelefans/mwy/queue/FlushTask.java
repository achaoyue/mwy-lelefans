package com.lelefans.mwy.queue;

import com.lelefans.mwy.game.kdou.GameConfig;
import com.lelefans.mwy.game.kdou.RoomContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class FlushTask implements Runnable {
    @Resource
    private GameConfig gameConfig;
    private boolean flag = true;
    @Override
    public void run() {
        long interval = 1000 / gameConfig.getFlushFrequency();
        while (flag){
            long startTime = System.currentTimeMillis();
            doJob();
            long endTime = System.currentTimeMillis();
            if(endTime - startTime > interval){
                log.info("刷新频率存在问题，本次耗时：{}",(endTime - startTime));
            }else{
                try {
                    Thread.sleep(interval - (endTime - startTime) );
                } catch (InterruptedException e) {
                    log.error("刷新任务暂停失败");
                }
            }
        }
    }

    public void doJob(){
        RoomContainer.getInstance().flushStatus();
    }

    @PostConstruct
    public void start(){
        new Thread(this).start();
    }

    public static void main(String[] args) throws InterruptedException {
        FlushTask flushTask = new FlushTask();
        flushTask.gameConfig = new GameConfig();
        flushTask.gameConfig.setFlushFrequency(50);
        flushTask.start();
        Thread.sleep(10000);
    }
}
