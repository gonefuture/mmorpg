package com.wan37.gameServer.event;




import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 9:45
 * @version 1.00
 * Description: 事件总线
 */
public class EventBus   {

    private static Map<Event, List<EventHandler>> listenerMap = new ConcurrentHashMap<>();

    /**
     *  订阅事件
     * @param event 事件的类对象
     * @param eventHandler 事件处理器
     */
    public static void subscribe(Event event, EventHandler eventHandler) {

        List<EventHandler> eventHandlerList = listenerMap.get(event);
        if (null == eventHandlerList) {
            eventHandlerList = new CopyOnWriteArrayList<>();
        }

        eventHandlerList.add( e -> {
            try {
                eventHandler.handle(e);
            } catch (Exception ex) {
                //记录错误日志
                ex.printStackTrace();
            }

        });

        listenerMap.put(event,eventHandlerList);
    }


    /**
     * 发布事件
     * @param event 事件
     */
    public static void publish(Event event) {

        listenerMap.get(event).forEach(eventHandler -> eventHandler.handle(event));
    }


    public static void close() throws Exception {
        //释放资源
        listenerMap.clear();
    }


}
