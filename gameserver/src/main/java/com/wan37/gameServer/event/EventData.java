package com.wan37.gameServer.event;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 19:55
 * @version 1.00
 * Description: 事件数据
 */
public interface EventData<T> {

    T getData();

    void setData(T t);


}
