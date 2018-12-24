package com.wan37.gameServer.game.sceneObject.service;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/6 15:55.
 *  说明：怪物掉落
 */

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.sceneObject.model.Drop;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.bag.model.Bag;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.game.things.service.ThingsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.util.ProbabilityTool.ProbabilityUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
    private ThingsService thingsService;


    @Resource
    private NotificationManager notificationManager;


    /**
     * 物品掉落
     */
    public void dropItem(Player player, SceneObject sceneObject) {

        // 掉落金钱，此处默认按怪物血量掉钱
        player.setMoney(player.getMoney()+ sceneObject.getHp().intValue());

        Bag bag = player.getBag();
        List<Drop> dropList = calculateDrop(sceneObject);
        for (Drop drop : dropList) {
            log.debug("drop {}", drop);
            int chance = drop.getChance();
            boolean flag = ProbabilityUtil.dropProbability(chance);
            if (flag) {
                Things things = thingsService.getThings(drop.getThingId());
                // 物品id

                // 唯一id用一个long类型存储，long类型有64位，我们使用它的低32位存储策划配置的物品表中的物品id，
                // 高32用来记录每获得一个物品就自增加1的序列order
                Long itemId =  (((long) new Date().getTime())<< 32) + (long)things.getId();
                Item item = new Item();
                item.setId(itemId);
                // 设置数量
                item.setCount(1);
                // 默认的位置索引为零
                item.setLocationIndex(0);
                item.setThings(things);
                if (bagsService.addItem(player,item)) {
                    // 广播玩家获得的掉落
                    notificationManager.<String>notifyPlayer(player,"玩家 "+player.getName()+" 获得了"
                            +things.getName()+ " x"+item.getCount());
                } else {
                    // 广播玩家获得的掉落
                    notificationManager.<String>notifyPlayer(player,"背包已满或物品不存在");
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