package com.wan37.gameServer.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 17:11
 * @version 1.00
 * Description: JavaLearn
 */

@Component
@Slf4j
public class GameCacheManager {

    private final Map<String, Object> cacheMap;
    //加载因子
    private  float loadFactor = 0.75f;

    /*
     在Java中，实现LRU策略不是很麻烦，我们可以使用LinkedHashMap这个类，它有两大好处：
     1. 它本身已经实现了按照访问顺序的存储，
     也就是说，它会把最近读取的数据放在前面，最不常读取的数据放在最后，当然它也可以按照插入顺序存储。
     2. 它本身有一个方法用于判断是否需要删除最不经常读取的数据，但是，该方法默认是不需要移除的，所以，我们需要继承重写这个方法。
     当缓存超过最大的缓存值后，执行这个方法。大家可以去看看这个类的API。
     */
    private GameCacheManager(){
        final int cacheSize = 16;
        //这里为true，表示按访问顺序存储，最常访问的数据会放在前面
        cacheMap = new ConcurrentHashMap<>(new LinkedHashMap<String, Object>(cacheSize, loadFactor,true){
            private static final long serialVersionUID = -7587080022442225813L;
            /**
             * 重写删除策略方法，当缓存容量大于给定的大小时，开始移除最不常访问的数据
             */
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Object> eldest) {
                return cacheMap.size() > cacheSize;
            }
        });
    }
    /**
     * 向缓存中添加数据
     * @param key
     * @param value
     */
    public void put(String key,Object value){
        if(key == null){
            return;
        }
        cacheMap.put(key, value);
    }

    public Object get(String key){
        if(key == null){
            return null;
        }
        Object obj = cacheMap.get(key);
        if(obj == null){
            return null;
        }
        return obj;
    }

    public void remove(String key){
        cacheMap.remove(key);
    }

    public Set<Map.Entry<String, Object>> getAll(){
        return cacheMap.entrySet();
    }



    private static class InstanceHolder {
        private static final GameCacheManager GAME_CACHE_MANAGER = new GameCacheManager();
    }


    public static GameCacheManager getInstance() {

        return InstanceHolder.GAME_CACHE_MANAGER;

    }
}
