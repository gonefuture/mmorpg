package com.wan37.gameServer.manager.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 17:00
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class TaskManager {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);


    /**
     *  设置定时任务
     */
    public Future<EventData> schedule(long delay, Callable<EventData> callback) {
        return executorService.schedule(callback,delay, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(long initDely , long delay , Runnable runnable) {
        return executorService.scheduleAtFixedRate(runnable,initDely,delay, TimeUnit.MILLISECONDS);
    }




    public void unregister(long tick_id) {

    }
    public void update_timer() {

    }


}
