package com.wan37.gameServer.entity;


import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.mysql.pojo.entity.TScene;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:13
 * @version 1.00
 * Description: 场景
 */

@Slf4j
@Data
public class GameScene  extends TScene {

    // 处于场景的玩家
    private Map<Long,Player> players = new ConcurrentHashMap<>();


    // 处于场景中的NPC
    private  Map<Long, NPC> npcs = new ConcurrentHashMap<>();

    // 处于场景中的怪物

    private Map<Long,Monster> monsters = new ConcurrentHashMap<>();



}
