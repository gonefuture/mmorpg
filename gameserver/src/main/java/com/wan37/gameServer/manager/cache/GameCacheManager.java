package com.wan37.gameServer.manager.cache;

import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 14:23
 * @version 1.00
 * Description: 缓存管理器接口
 */
public interface GameCacheManager<K,V> {

    /*
     * 获取缓存数据
     */
    V get(K key);

    /*
     添加数据
      */
    void put(K key, V value);


}
