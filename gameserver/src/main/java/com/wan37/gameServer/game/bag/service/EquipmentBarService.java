package com.wan37.gameServer.game.bag.service;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.roleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.game.things.service.ThingsService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


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


    @Resource
    private RolePropertyService rolePropertyService;




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

        // 改变玩家属性
        rolePropertyService.loadThingPropertyToPlayer(player,things);
        // 穿上装备
        player.getEquipmentBar().put(things.getPart(),item);

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
            Map<String,Item> equipmentBar = JSON.parseObject(equipmentBarString,
                    new TypeReference<Map<String,Item>>(){});
            log.debug("  equipmentBar{}",equipmentBar);
            equipmentBar.values()
                    .forEach( item ->player.getEquipmentBar().put(item.getThings().getPart(),item));
        }

    }




}
