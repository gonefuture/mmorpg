package com.wan37.gameserver.manager.cache;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 14:23
 * @version 1.00
 * Description: 缓存管理器接口
 */
public interface ICacheManager<K,V> {

    /*
     * 获取缓存数据
     */
    V get(K id);

    /*
     添加数据
      */
    void put(K id, V value);



}
