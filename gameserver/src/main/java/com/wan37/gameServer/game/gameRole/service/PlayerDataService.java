package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.RoleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.manager.BagsManager;
import com.wan37.gameServer.game.gameRole.model.Bags;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.service.ThingsService;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;

import com.wan37.mysql.pojo.entity.TItem;
import com.wan37.mysql.pojo.entity.TItemExample;
import com.wan37.mysql.pojo.mapper.TItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private BagsManager bagsManager;

    @Resource
    private TItemMapper tItemMapper;


    public Player getPlayer(String channelId) {
        return playerCacheMgr.get(channelId);
    }


    /**
     *  初始化角色
     * @param player 角色
     */
    public void initPlayer(Player player) {

        // 加载背包
        Bags bags = new Bags();
        bags.setPlayerId(player.getId());
        loadBags(player.getId(),bags);
        bagsManager.put(player.getId(), bags);

        // 加载物品装备
        thingsService.loadThings(player);

        // 加载属性
        rolePropertyService.loadRoleProperty(player);

    }

    private void loadBags(long playerId, Bags bags) {
        TItemExample tItemExample = new TItemExample();
        tItemExample.or().andPlayerIdEqualTo(playerId);
        List<TItem> tItemList = tItemMapper.selectByExample(tItemExample);
        tItemList.forEach( tItem -> {
            bags.getItemMap().put(tItem.getItemId(),tItem);
        });
    }


}
