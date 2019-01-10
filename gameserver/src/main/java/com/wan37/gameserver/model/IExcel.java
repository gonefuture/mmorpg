package com.wan37.gameserver.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:57
 * @version 1.00
 * Description: Excel接口
 */
public interface IExcel<T> {


    // 获取Excel配置数据的主键,用于做缓存的主键等
    T getKey();

}
