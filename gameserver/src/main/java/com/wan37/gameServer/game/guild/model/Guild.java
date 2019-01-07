package com.wan37.gameServer.game.guild.model;

import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.mysql.pojo.entity.TGuild;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 9:49
 * @version 1.00
 * Description: 工会实体
 */

@Data
@EqualsAndHashCode(callSuper=true)
public class Guild  extends TGuild{

    public Guild() {

    }

    public Guild(Integer id,String guildName, Integer level, Integer wareHouseSize) {
        this.setId(id);
        this.setName(guildName);
        this.setLevel(level);
        this.setWarehouseSize(wareHouseSize);
    }

    // 成员
    private Map<Long, Player> memberMap = new ConcurrentHashMap<>();

    // 公会仓库
    private Map<Integer, Item> warehouseMap =  new ConcurrentSkipListMap<>();


    // 请求加入公会的列表
    private Map<Long,PlayerJoinRequest> playerJoinRequestMap =  new ConcurrentSkipListMap<>();

    /**
     *   获取公会仓库物品
     * @param warehouseIndex 仓库格子索引
     * @return
     */
    public synchronized Item warehouseTake(Integer warehouseIndex) {
        return this.getWarehouseMap().get(warehouseIndex);
    }


    /**
     *  公会仓库增加物品
     * @param item 物品
     * @return 是否增加物品成功
     */
    public synchronized boolean warehouseAdd(Item item) {
        Map<Integer,Item> warehouseMap = this.getWarehouseMap();
        // 种类为3的物品为可堆叠的
        if (item.getThings().getKind() == 3) {
            for (int locationIndex=1; locationIndex <= this.getWarehouseSize(); locationIndex++) {
                Item i = warehouseMap.get(locationIndex);
                // 如果是用一种物品且堆叠未满
                if (i != null && i.getThings().getId().equals(item.getThings().getId())) {
                    i.setCount(i.getCount() + item.getCount());
                    return true;
                }
            }
        }


        // 遍历背包所有格子，如果是空格，将物品放入格子
        for (int locationIndex=1; locationIndex <= this.getWarehouseSize(); locationIndex++) {
            item.setLocationIndex(locationIndex);
            if (null == this.getWarehouseMap().putIfAbsent(locationIndex,item)) {
                return true;
            }
        }

        // 如果背包已满
        return false;
    }





}
