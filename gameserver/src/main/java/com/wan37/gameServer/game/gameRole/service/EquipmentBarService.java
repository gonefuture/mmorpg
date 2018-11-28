package com.wan37.gameServer.game.gameRole.service;

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


    public boolean equip(Player player, Integer equipmentId) {
        Things equipment = thingsService.getThings(equipmentId);
        log.debug("equipment {} ",equipment);

        player.getEquipmentBar().add(player,equipment);
        return true;
    }
}
