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
import com.wan37.gameServer.game.gameRole.service.BagsService;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.gameServer.game.things.service.ThingsService;
import com.wan37.gameServer.util.ProbabilityTool.ProbabilityUtil;
import com.wan37.mysql.pojo.entity.TItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <pre> </pre>
 */

@Service
public class MonsterDropsService {

    @Resource
    private BagsService bagsService;

    @Resource
    private ThingsService thingsService;


    /**
     * 物品掉落
     */
    public void dropItem(Player player, SceneObject sceneObject) {
        Bags bags = bagsService.getBagsByPlayerId(player.getId());
        List<Drop> dropList = calculateDrop(sceneObject);
        for (Drop drop : dropList) {
            int chance = drop.getChance();
            boolean flag = ProbabilityUtil.dropProbability(chance);
            if (flag) {
                Things things = thingsService.getThings(drop.getThingId());
                // 物品id
                String itemId = new Date().getTime() + "-" + things.getId();
                TItem tItem = new TItem();
                tItem.setItemId(itemId);
                tItem.setNumber(1);
                tItem.setPlayerId(player.getId());
                tItem.setThingsId(things.getId());
                bags.getItemMap().put(itemId, tItem);
            }
        }
    }

    /**
     * 计算物品掉落,获得
     */
    public List<Drop> calculateDrop(SceneObject sceneObject) {
        String dropString = sceneObject.getDrop();
        return JSON.parseArray(dropString, Drop.class);
    }


}