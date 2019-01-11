package com.wan37.gameserver.event;





import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 9:45
 * @version 1.00
 * Description: 事件总线
 */

@Slf4j
public class EventBus  {

    private static Map<Class<? extends Event>, List<EventHandler>> listenerMap = new ConcurrentHashMap<>();

    /**
     *  订阅事件
     * @param eventClass 事件的类对象类型
     * @param eventHandler 事件处理器
     */
    public static <E extends Event> void subscribe(Class<? extends Event> eventClass, EventHandler<E> eventHandler) {

        List<EventHandler> eventHandlerList = listenerMap.get(eventClass);
        if (null == eventHandlerList) {
            eventHandlerList = new CopyOnWriteArrayList<>();
        }

        eventHandlerList.add(eventHandler);

        listenerMap.put(eventClass,eventHandlerList);
    }


    /** 单独一个线程异步处理事件处理，同时保证了事件的循序 */
    private static ThreadFactory sceneThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("event-loop-%d").build();
    private static ExecutorService singleThreadSchedule = Executors.newSingleThreadScheduledExecutor(sceneThreadFactory);


    /**
     * 发布事件
     * @param event 事件
     */
    public static <E extends Event> void publish(E event) {
        log.debug("事件{}被抛出 ，listenerMap {}",event.getClass(),listenerMap);
        List<EventHandler> handlerList =  listenerMap.get(event.getClass());
        if (!Objects.isNull(handlerList)) {
            for (EventHandler eventHandler: handlerList) {
               singleThreadSchedule.execute( () -> eventHandler.handle(event));
            }
        }

    }




    public static void close() throws Exception {
        //释放资源
        listenerMap.clear();
    }


}
