package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.skills.model.Pet;
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



    public boolean callPet(Creature master, Creature target, GameScene gameScene, Integer petId) {
        SceneObject sceneObject = gameObjectService.getGameObject(petId);
        if (null == sceneObject)
            return false;
        Pet pet = new Pet();
        BeanUtils.copyProperties(sceneObject,pet);
        pet.setPetId(master.getId().intValue()+sceneObject.getId().intValue());
        pet.setTarget(target);
        gameScene.getMonsters().put(pet.getKey(),pet);
        // cd结束召唤兽就消失
        TimedTaskManager.schedule(pet.getRefreshTime(),()-> gameScene.getMonsters().remove(pet.getKey()));
        return true;
    }






}
