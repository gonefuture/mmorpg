package com.wan37.gameserver.game.trade.model;

import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.player.model.Player;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 15:02
 * @version 1.00
 * Description: 交易板，用于存放双发需要交易的东西
 */

@Data
public class TradeBoard {

    /** 发起交易的玩家 */
    private Player initiator;

    /** 接受交易的玩家 */
    private Player accepter;


    public TradeBoard(Player initiator, Player accepter) {
        this.initiator = initiator;
        this.accepter = accepter;
        // 初始化金币
        this.moneyMap = new ConcurrentHashMap<>();
        moneyMap.put(initiator.getId(),0);
        moneyMap.put(accepter.getId(),0);
        this.playerItems = new ConcurrentHashMap<>();
        playerItems.put(initiator.getId(),new ConcurrentHashMap<>());
        playerItems.put(accepter.getId(),new ConcurrentHashMap<>());
    }

    // 需要交易的物品,键为放置者，交易成功时物品将给另一方,key为玩家id
    private Map<Long, Map<Long,Item>> playerItems ;


    // 需要交易的金币，key为玩家id

    private Map<Long,Integer> moneyMap;
}
