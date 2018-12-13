package com.wan37.gameServer.game.gameInstance.model;

import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.scene.model.GameScene;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/26 16:57
 * @version 1.00
 * Description: 副本实体类
 */

@Data
public class GameInstance  extends GameScene {


    // 保存玩家进入副本前的场景id

    private Map<Long,Integer> playerFrom = new ConcurrentHashMap<>();


    // Boss 列表
    private List<Monster> bossList = new CopyOnWriteArrayList<>();



    // 当前守关Boss
    private Monster guardBoss;
}
