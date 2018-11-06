package com.wan37.gameServer.game.SceneObject.service;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/6 15:55.
 *  说明：怪物掉落
 */

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.SceneObject.model.Drop;
import com.wan37.gameServer.game.SceneObject.model.SceneObject;
import com.wan37.gameServer.game.gameRole.manager.BagsManager;
import com.wan37.gameServer.game.gameRole.model.Bags;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.modle.Things;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre> </pre>
 */

@Service
public class MonsterDropsService  {

    @Resource
    private BagsManager bagsManager;

    public void dropItem(Player player, Things things) {
        Bags bags = bagsManager.get(player.getId());


    }


    public List<Drop>  calculateDrop(SceneObject sceneObject) {
        String dropString = sceneObject.getDrop();
        return JSON.parseArray(dropString,Drop.class);
    }



}
