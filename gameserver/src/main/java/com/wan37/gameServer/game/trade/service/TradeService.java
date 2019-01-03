package com.wan37.gameServer.game.trade.service;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.TradeEvent;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.trade.manager.TradeManager;
import com.wan37.gameServer.game.trade.model.TradeBoard;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 12:24
 * @version 1.00
 * Description: mmorpg
 */

@Service
@Slf4j
public class TradeService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService sceneService;

    @Resource
    private BagsService bagsService;

    @Resource
    private NotificationManager notificationManager;




    /**
     *  向玩家发起交易邀请
     * @param ctx 通道上下文
     * @param targetId 目标玩家id
     */
    public void initiateTrade(ChannelHandlerContext ctx, Long targetId) {
        Player initiator = playerDataService.getPlayerByCtx(ctx);
        Player accepter = playerDataService.getOnlinePlayerById(targetId);
        TradeBoard tradeBoard = new TradeBoard(initiator, accepter);
        tradeBoard.setInitiator(initiator);
        tradeBoard.setAccepter(accepter);

        TradeManager.putTradeBoard(initiator.getId(),tradeBoard);
        TradeManager.putTradeBoard(accepter.getId(),tradeBoard);

        notificationManager.notifyPlayer(initiator,
                MessageFormat.format("你在向 {0} 发起交易请求，如果对方将于三分钟内不开始交易，交易请求将失效\n",accepter.getName()));
        notificationManager.notifyPlayer(accepter, MessageFormat.format(
                "{0} 向你发起交易请求，如果同意开始交易，请回复 `begin_trade`，交易请求将于三分钟后失效\n",
                initiator.getName()));
        // 交易事件
        EventBus.publish(new TradeEvent(initiator,accepter));
    }


    /**
     *  被邀请者开始交易
     * @param ctx 通道上下文
     */
    public void beginTrade(ChannelHandlerContext ctx) {
        Player accepter = playerDataService.getPlayerByCtx(ctx);
        TradeBoard tradeBoard = TradeManager.getTradeBoard(accepter.getId());
        if (null == tradeBoard) {
            notificationManager.notifyPlayer(accepter, "当前并没有交易");
            return;
        }

        Player initiator = tradeBoard.getInitiator();

        notificationManager.notifyPlayer(initiator,
                MessageFormat.format("{0} 同意了你的交易请求，请用`trade_goods` 和 `trade_money`指令来交易货物或金币\n",
                        accepter.getName()));

        notificationManager.notifyPlayer(accepter, "你已同意交易,请用`trade_goods` 和 `trade_money`指令来交易货物或金币\n");
    }


    /**
     *  交易金币
     * @param ctx 上下文
     * @param moneyNumber 金币数目
     */
    public void tradeMoney(ChannelHandlerContext ctx,Integer moneyNumber) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        TradeBoard tradeBoard = TradeManager.getTradeBoard(player.getId());
        if (null == tradeBoard) {
            notificationManager.notifyPlayer(player,"交易不存在,无法交易金币");
            return;
        }

        if (moneyNumber > player.getMoney()) {
            notificationManager.notifyPlayer(player,"你并没有那么多的金钱");
            return;
        }


        Map<Long,Integer> moneyMap = tradeBoard.getMoneyMap();
        moneyMap.put(player.getId(),moneyMap.get(player.getId())+moneyNumber);

        notificationManager.notifyPlayers(Arrays.asList(tradeBoard.getAccepter(),tradeBoard.getInitiator()),
                MessageFormat.format("{0}往交易栏放入了 {1} 个金币",player.getName(),moneyNumber));
    }

    /**
     *  交易物品
     * @param ctx 命令发起者上下文
     * @param bagIndex 玩家背包中的格子索引
     */
    public void tradeGoods(ChannelHandlerContext ctx, Integer bagIndex) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        TradeBoard tradeBoard = TradeManager.getTradeBoard(player.getId());
        if (null == tradeBoard) {
            notificationManager.notifyPlayer(player,"交易不存在，无法交易物品");
            return;
        }

        Item item = player.getBag().getItemMap().get(bagIndex);
        if (null == item) {
            notificationManager.notifyPlayer(player,"你背包里没有这个物品");
            return;
        }

        // 放入交易栏
        Map<Long,Item> itemMap = tradeBoard.getPlayerItems().get(player.getId());
        itemMap.put(item.getId(),item);
        tradeBoard.getPlayerItems().put(player.getId(),itemMap) ;

        notificationManager.notifyPlayers(Arrays.asList(tradeBoard.getAccepter(),tradeBoard.getInitiator()),
                MessageFormat.format("{0}往交易栏放入了 物品{1} ",player.getName(),item.getThings().getName()));
    }

    /**
     *  确认交易
     * @param ctx 命令发起者上下文
     */
    public void confirmTrade(ChannelHandlerContext ctx) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        TradeBoard tradeBoard = TradeManager.getTradeBoard(player.getId());
        if (null == tradeBoard) {
            notificationManager.notifyPlayer(player,"交易不存在，无法确认交易");
            return;
        }
        Player initiator = tradeBoard.getInitiator();
        Player accepter = tradeBoard.getAccepter();
        // 将交易栏的金币放入玩家钱袋,发起者的金币放入接收者，接收者的金币放入发起者
        initiator.moneyChange(tradeBoard.getMoneyMap().get(accepter.getId()));
        // 玩家扣钱
        accepter.moneyChange(- tradeBoard.getMoneyMap().get(accepter.getId()));

        accepter.moneyChange(tradeBoard.getMoneyMap().get(initiator.getId()));
        // 玩家扣钱
        initiator.moneyChange( - tradeBoard.getMoneyMap().get(initiator.getId()));

        // 物品放入玩家背包,发起者的物品放入接收者背包，接收者的物品放入发起者背包
        tradeBoard.getPlayerItems().get(initiator.getId()).values().forEach(
                item -> {
                    bagsService.addItem(accepter,item);
                    // 从背包拿出物品
                    bagsService.removeItem(initiator,item.getLocationIndex());
                }
        );
        tradeBoard.getPlayerItems().get(accepter.getId()).values().forEach(
                item -> {
                    bagsService.addItem(initiator,item);
                    // 从背包拿出物品
                    bagsService.removeItem(accepter,item.getLocationIndex());
                }
        );

        notificationManager.notifyPlayers(Arrays.asList(tradeBoard.getAccepter(),tradeBoard.getInitiator()),
                "交易成功，双方已收到相应的物品和装备");

    }
}
