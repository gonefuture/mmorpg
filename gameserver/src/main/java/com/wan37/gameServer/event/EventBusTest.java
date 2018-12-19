package com.wan37.gameServer.event;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/19 18:13
 * @version 1.00
 * Description: 事件总线测试
 */


public class EventBusTest {


    {
        EventBus.bind(EventBus.class, (handler, eventData1) -> System.out.println(eventData1.getData()));
    }

    static  class EventA{

    }

    static class  ContextA implements EventData<String> {
        private String  param = "";

        @Override
        public String getData() {
            return param;
        }

        @Override
        public void setData(String t) {
            this.param = t;
        }
    }


    public static void main(String[] args) {

        EventBusTest eventBusTest = new EventBusTest();
        eventBusTest.ContextAService();


    }



    public void  ContextAService() {

        EventData<String> eventData = new ContextA();
        eventData.setData("hello");
        EventBus.post(EventBus.class,eventData);
    }


}
