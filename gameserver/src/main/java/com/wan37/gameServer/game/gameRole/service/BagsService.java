package com.wan37.gameServer.game.gameRole.service;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/5 10:40.
 *  说明： 背包管理
 */

import com.wan37.gameServer.game.gameRole.manager.BagsManager;
import com.wan37.gameServer.game.gameRole.model.Bags;
import com.wan37.gameServer.game.gameRole.model.BagsCell;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre> </pre>
 */

@Slf4j
@Service
public class BagsService {

    @Resource
    private BagsManager bagsManager;

    @Resource
    private ThingsCacheMgr thingsCacheMgr;


    /**
     *
     *  展示背包格子
     */
    public List<BagsCell> show(Long playerId) {
        List<BagsCell> bagsCellList = new ArrayList<>();
        Bags bags = bagsManager.get(playerId);
        if (bags != null) {
            bags.getItemMap().values().forEach( tItem -> {
                Things things = thingsCacheMgr.get(tItem.getThingsId());
                BagsCell bagsCell = new BagsCell();
                bagsCell.setThings(things);
                bagsCell.setTItem(tItem);
                bagsCellList.add(bagsCell);
            });
        }
        return bagsCellList;
    }


    /**
     *  获取物品的物品信息
     */
    public Things getThings(Long playerId, String itemId) {
        Bags bags = bagsManager.get(playerId);
        TItem tItem = bags.getItemMap().get(itemId);
        if (tItem == null)
            return null;
        return thingsCacheMgr.get(tItem.getThingsId());
    }


    public Bags getBagsByPlayerId(Long playerId) {
        return bagsManager.get(playerId);
    }
}
