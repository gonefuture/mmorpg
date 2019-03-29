package com.wan37.gameserver.game.bag.model;

import lombok.Data;


import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/2/22 18:25
 * @version 1.00
 * Description: 物品集，掉落的物品，地图上的任务品物，草药矿物都可以包含进去。
 */

@Data
public class ItemSet {

    private Long itemSetId;

    private Map<Long,Item> itemMap;


}
