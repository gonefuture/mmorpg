package com.wan37.gameserver.game.scene.model;



import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wan37.gameserver.game.sceneObject.model.Monster;

import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.sceneObject.model.NPC;
import com.wan37.gameserver.util.excel.EntityName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:13
 * @version 1.00
 * Description: 场景
 */

@Slf4j
@Data
public class GameScene  {

    @EntityName(column = "ID")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "状态")
    private String state;

    @EntityName(column = "相邻场景")
    private String neighbors = "";

    @EntityName(column = "地图类型")
    private Integer type;

    @EntityName(column = "场景对象")
    private String gameObjectIds;


    @EntityName(column = "副本时间")
    private Long instanceTime;


    /** 处于场景的玩家,key为player_id */
    private Map<Long,Player> players = new ConcurrentHashMap<>();


    /** 处于场景中的NPC */
    private  Map<Long, NPC> npcs = new ConcurrentHashMap<>();

    /** 处于场景中的怪物 */
    private Map<Long,Monster> monsters = new ConcurrentHashMap<>();

    public String display() {
        return MessageFormat.format("id:{0}  name:{1}"
                ,this.getId(),this.getName());
    }


    private static ThreadFactory sceneThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("scene-loop-%d").build();
    /** 通过一个场景一个线程处理器的方法保证每个场景的指令循序 */
    ExecutorService singleThreadSchedule = Executors.newSingleThreadScheduledExecutor(sceneThreadFactory);


}
