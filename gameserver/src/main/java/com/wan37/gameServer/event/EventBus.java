package com.wan37.gameServer.event;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 9:45
 * @version 1.00
 * Description: 事件总线
 */
public class EventBus   {

    private static Map<Class<?>,EventHandler> handlerMap = new ConcurrentHashMap<>();


    public static void bind(Class<?> eventClass, EventHandler eventHandler) {
        handlerMap.put(eventClass, ((clazz, context) -> {
            try {
                eventHandler.handle(clazz, context);
            } catch (Exception e) {
                //记录错误日志
                e.printStackTrace();
            }
            //打印处理器执行日志
        }));
    }


    /**
     * 触发事件
     * @param
     */
    public static  <T> void post(Class<?> EventClass,EventData<T> eventData) {

        handlerMap.get(EventClass).handle(EventClass, eventData);
    }


    public static void close() throws Exception {
        //释放资源
        handlerMap.clear();
    }


}
