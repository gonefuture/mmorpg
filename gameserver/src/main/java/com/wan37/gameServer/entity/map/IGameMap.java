package com.wan37.gameServer.entity.map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 16:49
 * @version 1.00
 * Description: JavaLearn
 */
public interface IGameMap<T> {


    // 初始化地图并返回
    T [][] init();

    // 设置一个地图
    T [][] getMap();

    // 设置一个地图
    void setMap(T[][] map);


    String StringMap();

}
