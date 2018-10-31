package com.wan37.gameServer.game.SceneObject.service;

import com.wan37.gameServer.game.SceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.SceneObject.model.SceneObject;
import com.wan37.mysql.pojo.entity.TGameObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 18:30
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class GameObjectService {
    @Resource
    private GameObjectCacheMgr gameObjectCacheMgr;

    public SceneObject getGameObject(long gameObjectId) {
        return gameObjectCacheMgr.get(gameObjectId);
    }


    public void cacheGameObject(long gameObjectId, SceneObject sceneObject) {
        gameObjectCacheMgr.put(gameObjectId, sceneObject);
    }


    /**
     *  检查游戏对象是否死亡。如果传入的参数为空，则返回否
     *  游戏对象为state属性为-1时，表示死亡状态。
     */
    public boolean isGameObjectDead(TGameObject tGameObject){
        return tGameObject != null && tGameObject.getState() != -1;
    }


}
