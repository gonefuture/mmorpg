package com.wan37.gameServer.game.skills.service;

import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.sceneObject.service.GameObjectService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.skills.model.Pet;
import com.wan37.gameServer.model.Creature;
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



    public boolean callPet(Creature master, GameScene gameScene, Integer petId, Creature target) {
        SceneObject sceneObject = gameObjectService.getGameObject(petId);
        if (null == sceneObject)
            return false;
        Pet pet = new Pet();
        BeanUtils.copyProperties(sceneObject,pet);
        pet.setPetId(master.getId().intValue()+sceneObject.getId().intValue());
        pet.setTarget(target);
        gameScene.getMonsters().put(pet.getKey(),pet);
        return true;
    }






}
