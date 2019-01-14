package com.wan37.gameserver.game.trade.model;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.mysql.pojo.entity.TAuctionItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 17:52
 * @version 1.00
 * Description: 拍卖品
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Getter()
public class AuctionItem extends TAuctionItem {




    private Map<Long,Integer> biddersMap
            = new ConcurrentHashMap<>();


    public synchronized void addBidder(Player player,Integer auctionPrice) {
        // 加载竞拍者数据
        if (!this.getBidders().isEmpty() && biddersMap.isEmpty()) {
            biddersMap = JSON.parseObject(this.getBidders(),new TypeReference<ConcurrentHashMap<Long,Integer>>(){});
        }

        biddersMap.put(player.getId(),auctionPrice);
        // 将竞拍者信息保存在数据库更新做准备
        this.setBidders(JSON.toJSONString(biddersMap));
    }



}
