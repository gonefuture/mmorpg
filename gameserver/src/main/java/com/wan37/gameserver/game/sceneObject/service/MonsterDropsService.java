package com.wan37.gameserver.game.sceneObject.service;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/6 15:55.
 *  说明：怪物掉落
 */

import com.alibaba.fastjson.JSON;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.sceneObject.model.Drop;
import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.bag.model.Bag;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ProbabilityTool.ProbabilityUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * <pre> </pre>
 */

@Service
@Slf4j
public class MonsterDropsService {

    @Resource
    private BagsService bagsService;

    @Resource
    private ThingInfoService thingInfoService;


    @Resource
    private NotificationManager notificationManager;


    /**
     * 物品掉落
     */
    void dropItem(Player player, SceneObject sceneObject) {

        // 掉落金钱，此处默认按怪物mp掉钱
        player.moneyChange(sceneObject.getMp().intValue());


        // 掉落经验，，此处默认按怪物mp来计算
        player.addExp(sceneObject.getMp().intValue());

        Bag bag = player.getBag();
        List<Drop> dropList = calculateDrop(sceneObject);
        for (Drop drop : dropList) {
            log.debug("drop {}", drop);
            int chance = drop.getChance();
            boolean flag = ProbabilityUtil.dropProbability(chance);
            if (flag) {
                ThingInfo thingInfo = thingInfoService.getThingInfo(drop.getThingId());
                // 随机的物品id
                Long itemId = thingInfoService.generateItemId();
                Item item = new Item(itemId,1,thingInfo);
                item.setThingInfo(thingInfo);
                if (!bagsService.addItem(player,item)) {
                    notificationManager.notifyPlayer(player,
                            MessageFormat.format("背包已满，放不下物品 {0}", thingInfo.getName()));
                }
                log.debug("bag {}", bag);
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