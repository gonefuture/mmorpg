package com.wan37.gameServer.manager.task;

import com.wan37.gameServer.event.Event;
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
public class TimedTaskManager {

    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);


    /**
     *  设置定时任务
     * @param delay 延迟执行时间，单位毫秒
     * @param callback 任务
     * @return 一个带结果的future
     */
    public static Future<Event> scheduleWithData(long delay, Callable<Event> callback) {
        return executorService.schedule(callback,delay, TimeUnit.MILLISECONDS);
    }




    /**
     *  设置定时任务
     * @param delay 延迟执行时间，单位毫秒
     * @param runnable 任务
     * @return 一个不带结果的future
     */
    public static Future schedule(long delay, Runnable runnable) {
        return executorService.schedule(runnable,delay, TimeUnit.MILLISECONDS);
    }


    /**
     *  按固定的周期执行任务
     * @param initDelay 延时开始第一次任务的时间
     * @param delay     执行间隔
     * @param runnable 任务
     * @return    一个不带结果的future
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(long initDelay , long delay , Runnable runnable) {
        return executorService.scheduleAtFixedRate(runnable,initDelay,delay, TimeUnit.MILLISECONDS);
    }


    /**
     *  按照固定的延迟执行任务（即执行完上一个再执行下一个）
     * @param initDelay 延时开始第一次任务的时间
     * @param delay     执行间隔
     * @param runnable 任务
     *
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(long initDelay , long delay , Runnable runnable) {
        return executorService.scheduleWithFixedDelay(runnable,initDelay,delay, TimeUnit.MILLISECONDS);
    }


}
