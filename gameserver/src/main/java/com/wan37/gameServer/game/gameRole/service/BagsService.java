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
}
