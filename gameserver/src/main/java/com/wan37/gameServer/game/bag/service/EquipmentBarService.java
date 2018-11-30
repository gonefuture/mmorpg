package com.wan37.gameServer.game.bag.service;

import com.alibaba.fastjson.JSON;

import com.google.common.base.Strings;
import com.wan37.gameServer.game.bag.model.EquipmentBar;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.game.things.service.ThingsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 17:30
 * @version 1.00
 * Description: 装备栏相关服务
 *
 */

@Service
@Slf4j
public class EquipmentBarService {



    @Resource
    private ThingsService thingsService;


    @Resource
    private BagsService bagsService;



    /**
     *  穿上装备
     * @param player 玩家
     * @param index 背包格子索引
     * @return 成功或失败
     */
    public boolean equip(Player player, Integer index) {
        Item item = player.getBag().getItemMap().get(index);
        if (null == item)
              return false;

        Things things = item.getThings();

        // 判断是否是装备,装备的种类代号为 1
        if ( null == things || things.getKind() != 1)
            return false;

        Things equipment = thingsService.getThings(things.getId());
        log.debug("穿上装备 equipment {} ",equipment);

        // 穿上装备，改变玩家属性
        player.getEquipmentBar().add(player,item);

        // 从背包移除物品
        bagsService.removeItem(player,index);
        return true;
    }








    /**
     *  加载装备
     */
    public void load(Player player) {
        String equipmentBarString = player.getEquipments();
        if (!Strings.isNullOrEmpty(equipmentBarString)) {
            EquipmentBar equipmentBar = JSON.parseObject(equipmentBarString,
                    EquipmentBar.class);
            log.debug("  equipmentBar{}",equipmentBar);
            equipmentBar.getEquipmentMap().values()
                    .forEach( item ->player.getEquipmentBar().add(player,item));
        }

    }




}
