package com.wan37.gameserver.game.bag.service;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Strings;
import com.wan37.common.entity.Msg;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.roleProperty.service.RolePropertyService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;

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
    private ThingInfoService thingInfoService;


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
        Map<String, Item>  equipmentBar = player.getEquipmentBar();

        Item item = player.getBag().getItemMap().get(index);
        if (null == item) {
              return false;
        }
        ThingInfo thingInfo = item.getThingInfo();

        // 判断是否是装备,装备的种类代号为 1
        if ( null == thingInfo || thingInfo.getKind() != 1) {
            return false;
        }

        Item preItem = equipmentBar.get(thingInfo.getPart());
        if ( null != preItem) {
            // 如果原来这给部位有装备，移除原先装备的属性
            rolePropertyService.loadThingPropertyToPlayer(player,preItem.getThingInfo());
        }

        ThingInfo equipment = thingInfoService.getThingInfo(thingInfo.getId());

        // 改变玩家属性
        rolePropertyService.loadThingPropertyToPlayer(player, thingInfo);

        // 穿上装备
        player.getEquipmentBar().put(thingInfo.getPart(),item);

        // 从背包移除物品
        bagsService.removeItem(player,index);

        if ( null != preItem) {
            // 将原先的物品放入背包
            bagsService.addItem(player,preItem);
        }
        return true;
    }





    /**
     *      卸下装备
     * @param player 玩家
     * @param part 需要卸下装备的部位
     * @return 是否卸下装备
     */
    public Msg removeEquip(Player player, String part)  {
        Map<String,Item> equipmentBar = player.getEquipmentBar();
        Item item = equipmentBar.get(part);
        if (item == null) {
            return new Msg(404,"你身上没有穿戴这件装备");
        }
        // 移除属性增益,放入背包
        if ( equipmentBar.remove(part,item)
                &&bagsService.addItem(player,item)
                && rolePropertyService.removeThingPropertyForPlayer(player,item.getThingInfo())) {
            return new Msg(200,"卸下装备成功");
        } else {
            return new Msg(401,"背包已满");
        }

    }



    /**
     *  加载装备
     */
    public void load(Player player) {
        String equipmentBarString = player.getEquipments();
        if (!Strings.isNullOrEmpty(equipmentBarString)) {
            Map<String,Item> equipmentBar = JSON.parseObject(equipmentBarString,
                    new TypeReference<Map<String,Item>>(){});
            log.debug("  equipmentBarString {}",equipmentBarString);
            log.debug("  equipmentBar{}",equipmentBar);
            // 很重要，将从数据库还原的装备加载到角色
            player.setEquipmentBar(equipmentBar);

            equipmentBar.values()
                    .forEach( item ->
                            // 改变玩家属性
                            rolePropertyService.loadThingPropertyToPlayer(player,item.getThingInfo()));
        }

    }




}
