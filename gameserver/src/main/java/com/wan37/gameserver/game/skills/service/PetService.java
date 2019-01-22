package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.skills.model.Pet;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.manager.task.TimedTaskManager;
import com.wan37.gameserver.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/24 11:45
 * @version 1.00
 * Description: mmorpg
 */

@Service
@Slf4j
public class PetService {

    @Resource
    private GameObjectService gameObjectService;

    @Resource
    private NotificationManager notificationManager;




    /**
     *  召唤宠物，宠物的信息模板是从场景对象中来的
     * @param master    宠物主人
     * @param gameScene 场景
     * @param petId 宠物id
     * @return  是否召唤成功
     */
    public boolean callPet(Creature master, GameScene gameScene, Integer petId) {
        SceneObject sceneObject = gameObjectService.getGameObject(petId);
        if (null == sceneObject) {
            return false;
        }
        Pet pet = new Pet();
        BeanUtils.copyProperties(sceneObject,pet);
        pet.setPetId(gameObjectService.generateObjectId());

        gameScene.getMonsters().put(pet.getPetId(),pet);
        master.setPet(pet);
        pet.setMaster(master);
        log.debug("宠物的目标{}",master.getPet().getName());

        notificationManager.notifyScene(gameScene,
                MessageFormat.format("{0} 召唤了 {1}",master.getName(),pet.getName()));

        // cd结束召唤兽就消失
//        TimedTaskManager.singleThreadSchedule(pet.getRefreshTime(),
//                ()-> {
//            notificationManager.notifyCreature(master,"你的宠物已经解散                                                ");
//            gameScene.getMonsters().remove(pet.getPetId());
//
//        });
        return true;
    }






}
