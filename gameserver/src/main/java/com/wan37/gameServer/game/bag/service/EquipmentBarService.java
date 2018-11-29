package com.wan37.gameServer.game.bag.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.bag.model.EquipmentBar;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.game.things.service.ThingsService;
import jdk.internal.joptsimple.internal.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


    /**
     *  穿上装备
     */
    public boolean equip(Player player, Integer equipmentId) {
        Things equipment = thingsService.getThings(equipmentId);
        log.debug("equipment {} ",equipment);

        player.getEquipmentBar().add(player,equipment);
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
            player.setEquipmentBar(equipmentBar);
        }

    }




}
