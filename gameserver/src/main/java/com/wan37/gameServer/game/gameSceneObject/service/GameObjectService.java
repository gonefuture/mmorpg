package com.wan37.gameServer.game.gameSceneObject.service;


import com.wan37.gameServer.game.gameSceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.gameSceneObject.model.NPC;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

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


    /**
     *      获得场景配置的场景对象
     */
    public GameScene getSceneObject(GameScene gameScene) {
        String  gameObjectIds = gameScene.getGameObjectIds();
        Arrays.stream(gameObjectIds.split(","))
                .map(Long::valueOf)
                .map( this::getGameObject)
                .forEach( sceneObject -> {
                    if ( sceneObject.getRoleType() == 1) {
                        NPC npc = new NPC();
                        BeanUtils.copyProperties(sceneObject,npc);
                        gameScene.getNpcs().put(sceneObject.getId(), npc);
                    }
                    if (sceneObject.getRoleType() == 2) {
                        Monster monster = new Monster();
                        BeanUtils.copyProperties(sceneObject,monster);
                        gameScene.getMonsters().put(sceneObject.getId(), monster);
                    }
                }
        );
        return gameScene;
    }


}
