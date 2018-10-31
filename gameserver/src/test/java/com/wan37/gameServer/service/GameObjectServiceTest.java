package com.wan37.gameServer.service;

import com.wan37.gameServer.game.SceneObject.service.GameObjectService;
import com.wan37.mysql.pojo.entity.TGameObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/9 14:55
 * @version 1.00
 * Description: mmorpg
 */
public class GameObjectServiceTest {


    @Test
    public void testIsGameObjectDead() {
        GameObjectService gameObjectService = new GameObjectService();
        // 构建测试参数
        TGameObject tGameObject = new TGameObject();
        tGameObject.setState(-1);
        // 属性状态为-1，方法返回false，表示游戏对象死亡。
        Assert.assertFalse(gameObjectService.isGameObjectDead(tGameObject));

        // 参数为null，返回false
        Assert.assertFalse(gameObjectService.isGameObjectDead(null));
    }

}
