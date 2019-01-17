package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.skills.model.Pet;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.manager.task.TimedTaskManager;
import com.wan37.gameserver.model.Creature;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/24 11:45
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class PetService {

    @Resource
    private GameObjectService gameObjectService;



    @Resource
    private NotificationManager notificationManager;


    /**
     *  召唤宠物，宠物的信息模板是从场景对象中来的
     * @param master    宠物主人
     * @param target    宠物攻击目标
     * @param gameScene 场景
     * @param petId 宠物id
     * @return  是否召唤成功
     */
    public boolean callPet(Creature master, Creature target, GameScene gameScene, Integer petId) {
        SceneObject sceneObject = gameObjectService.getGameObject(petId);
        if (null == sceneObject) {
            return false;
        }
        Pet pet = new Pet();
        BeanUtils.copyProperties(sceneObject,pet);
        pet.setPetId(master.getId() >> 32+sceneObject.getId());
        // 与主人目标一致
        pet.setTarget(target);
        gameScene.getMonsters().put(pet.getPetId(),pet);
        // cd结束召唤兽就消失
        TimedTaskManager.singleThreadSchedule(pet.getRefreshTime(),
                ()-> {
            notificationManager.notifyCreature(master,"你的宠物已经解散                                                ");
            gameScene.getMonsters().remove(pet.getPetId());

        });
        return true;
    }






}
