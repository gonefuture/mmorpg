package com.wan37.gameserver.game.sceneObject.service;


import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.TalkWithEvent;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.quest.manager.QuestManager;
import com.wan37.gameserver.game.quest.model.Quest;
import com.wan37.gameserver.game.sceneObject.manager.GameObjectManager;
import com.wan37.gameserver.game.sceneObject.model.Monster;
import com.wan37.gameserver.game.sceneObject.model.NPC;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.sceneObject.model.SceneObjectType;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.SnowFlake;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 18:30
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class GameObjectService {
    @Resource
    private GameObjectManager gameObjectManager;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private QuestManager questManager;




    public SceneObject getGameObject(long gameObjectId) {
        return gameObjectManager.get(gameObjectId);
    }


    public void cacheGameObject(long gameObjectId, SceneObject sceneObject) {
        gameObjectManager.put(gameObjectId, sceneObject);
    }




    /**
     *  场景对象死亡后处理
     * @param sceneObject 场景对象死亡后处理
     * @return
     */
    public boolean sceneObjectAfterDead(SceneObject sceneObject) {
        if (sceneObject.getHp() <= 0) {
            // 重要，设置死亡时间
            sceneObject.setDeadTime(System.currentTimeMillis());
            sceneObject.setHp(0L);
            sceneObject.setState(-1);
            // 重要，清空对象当前目标
            sceneObject.setTarget(null);
            return true;
        } else{
            return false;
        }
    }






    /**
     *      获得场景配置的场景对象
     */
    public GameScene initSceneObject(GameScene gameScene) {
        String  gameObjectIds = gameScene.getGameObjectIds();
        Arrays.stream(gameObjectIds.split(","))
                .map(Long::valueOf)
                .map( this::getGameObject)
                .forEach( sceneObject -> {
                    if ( sceneObject.getRoleType().equals(SceneObjectType.NPC.getType())) {
                        NPC npc = new NPC();
                        BeanUtils.copyProperties(sceneObject,npc);
                        gameScene.getNpcs().put(sceneObject.getId(), npc);
                    }
                    if (sceneObject.getRoleType().equals(SceneObjectType.WILD_MONSTER.getType()) ) {
                        Monster monster = new Monster();
                        BeanUtils.copyProperties(sceneObject,monster);
                        gameScene.getMonsters().put(sceneObject.getId(), monster);
                    }
                }
        );
        return gameScene;
    }


    /**
     *  与NPC谈话
     * @param ctx 上下文
     */
    public void talkWithNPC(Long npcId,ChannelHandlerContext ctx) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        SceneObject sceneObject = getGameObject(npcId);
        talk(player,sceneObject);

        // 谈话事件
        EventBus.publish(new TalkWithEvent(player,npcId));

    }


    /**
     *      场景对象谈话
     * @param player 玩家
     * @param sceneObject 场景对象
     */
    public void talk(Player player, SceneObject sceneObject) {
        notificationManager.notifyPlayer(player,sceneObject.getTalk());
    }



    /**
     *  场景对象id生成
     */
    public Long generateObjectId() {
        // 使用推特的雪花算法
        SnowFlake snowFlake = new SnowFlake(1, 1);
        return snowFlake.nextId();
    }

    public List<Quest> npcQuest(Long npcId) {
        SceneObject sceneObject = getGameObject(npcId);
        String quests = sceneObject.getQuests();
        if (Objects.isNull(quests)) {
            return Collections.emptyList();
        }
        return Arrays.stream(quests.split(",")).
                map(Integer::valueOf)
                .map(QuestManager::getQuest)
                .collect(Collectors.toList());
    }
}
