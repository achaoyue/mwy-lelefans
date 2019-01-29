package com.lelefans.mwy.game.kdou;

import com.lelefans.mwy.enums.GameRoomStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class SystemMachine implements Runnable {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    List<Gamer> gamers = new Vector<>();

    public SystemMachine() {
        executor.scheduleAtFixedRate(this, 0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        Iterator<Gamer> iterator = gamers.iterator();
        while (iterator.hasNext()){
            Gamer next = iterator.next();
            if(next == null || next.getGameRoom() == null || next.getGameRoom().getStatus() == GameRoomStatus.Game_Over){
                iterator.remove();
                continue;
            }
            if(next.getGameRoom().getStatus() == GameRoomStatus.Wait_Start){
                continue;
            }
            changeGamer(next);

        }
    }

    private void changeGamer(Gamer gamer) {
        GameRoom gameRoom = gamer.getGameRoom();

    }

    public void AddGamer(Gamer gamer) {
        gamers.add(gamer);
    }
}
