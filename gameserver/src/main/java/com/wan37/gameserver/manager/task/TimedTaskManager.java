package com.wan37.gameserver.manager.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wan37.gameserver.event.Event;
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

    private static ThreadFactory scheduledThreadPoolFactory = new ThreadFactoryBuilder()
            .setNameFormat("scheduledThreadPool-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    private static ScheduledExecutorService ScheduledThreadPool =
            Executors.newScheduledThreadPool( Runtime.getRuntime().availableProcessors()*2+1,scheduledThreadPoolFactory);


    /**
     *  单线程循环执行器
     */
    private static ThreadFactory singleThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("singleThread-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    public static ScheduledExecutorService singleThreadSchedule =  Executors.newSingleThreadScheduledExecutor(singleThreadFactory);





    /**
     *  设置定时任务
     * @param delay 延迟执行时间，单位毫秒
     * @param callback 任务
     * @return 一个带结果的future
     */
    public static Future<Event> scheduleWithData(long delay, Callable<Event> callback) {
        return ScheduledThreadPool.schedule(callback,delay, TimeUnit.MILLISECONDS);
    }


    /**
     *  设置单线程定时任务
     * @param delay 延迟执行时间，单位毫秒
     * @param runnable 任务
     * @return 一个不带结果的future
     */
    public static Future singleThreadSchedule(long delay, Runnable runnable) {
        return singleThreadSchedule.schedule(runnable,delay, TimeUnit.MILLISECONDS);
    }



    /**
     *  设置定时任务(注意这个多线程的)
     * @param delay 延迟执行时间，单位毫秒
     * @param runnable 任务
     * @return 一个不带结果的future
     */
    public static Future schedule(long delay, Runnable runnable) {
        return ScheduledThreadPool.schedule(runnable,delay, TimeUnit.MILLISECONDS);
    }


    /**
     *  按固定的周期执行任务
     * @param initDelay 延时开始第一次任务的时间
     * @param delay     执行间隔
     * @param runnable 任务
     * @return    一个不带结果的future
     */
    public static ScheduledFuture<?> scheduleAtFixedRate(long initDelay , long delay , Runnable runnable) {
        return ScheduledThreadPool.scheduleAtFixedRate(runnable,initDelay,delay, TimeUnit.MILLISECONDS);
    }


    /**
     *  按照固定的延迟执行任务（即执行完上一个再执行下一个）
     * @param initDelay 延时开始第一次任务的时间
     * @param delay     执行间隔
     * @param runnable 任务
     *
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(long initDelay , long delay , Runnable runnable) {
        return ScheduledThreadPool.scheduleWithFixedDelay(runnable,initDelay,delay, TimeUnit.MILLISECONDS);
    }


}
