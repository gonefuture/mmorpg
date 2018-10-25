package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.RoleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.service.ThingsService;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:07
 * @version 1.00
 * Description: mmorpg
 */
@Service
@Slf4j
public class PlayerDataService {


    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private ThingsService thingsService;

    @Resource
    private RolePropertyService rolePropertyService;


    public Player getPlayer(String channelId) {
        return playerCacheMgr.get(channelId);
    }


    /**
     *  初始化角色
     * @param player 角色
     */
    public void initPlayer(Player player) {
        // 加载物品装备
        thingsService.loadThings(player);

        // 加载属性
        rolePropertyService.loadRoleProperty(player);



    }


}
