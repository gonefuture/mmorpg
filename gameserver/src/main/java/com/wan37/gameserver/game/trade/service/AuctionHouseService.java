package com.wan37.gameserver.game.trade.service;

import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.mail.model.DefaultSender;
import com.wan37.gameserver.game.mail.service.MailService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.game.trade.manager.AuctionHouseManager;
import com.wan37.gameserver.game.trade.model.AuctionItem;
import com.wan37.gameserver.game.trade.model.AuctionMode;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.mysql.pojo.entity.TPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 18:08
 * @version 1.00
 * Description: 拍卖行服务
 */

@Service
@Slf4j
public class AuctionHouseService  {

    @Resource
    private BagsService bagsService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private AuctionHouseManager auctionHouseManager;

    @Resource
    private ThingInfoService thingInfoService;

    @Resource
    private MailService mailService;

    @Resource
    private PlayerDataService playerDataService;


    public void pushAuction(Player player, Integer bagIndex,Integer auctionMode, Integer auctionPrice) {
        Item item = bagsService.removeItem(player,bagIndex);
        if (Objects.isNull(item)) {
            return;
        }
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setNumber(item.getCount());
        auctionItem.setThingInfoId(item.getThingInfo().getId());
        auctionItem.setAuctionMode(auctionMode);
        auctionItem.setAuctionPrice(auctionPrice);
        auctionItem.setPushTime(new Date());
        log.debug("{}",player.getId());
        auctionItem.setPublisherId(player.getId());
        auctionHouseManager.putAuctionItem(auctionItem);
        notificationManager.notifyPlayer(player, MessageFormat.format("发布物品{0}成功,初始化价格是{1}",
                item.getThingInfo().getName(),auctionPrice));
    }


    /**
     *  竞拍物品
     * @param player    玩家
     * @param auctionId 拍卖id
     * @param price 叫价
     */
    public void bid(Player player,Integer auctionId, Integer price) {
        AuctionItem auctionItem = AuctionHouseManager.getAuctionItem(auctionId);

        if (Objects.isNull(auctionItem)) {
            notificationManager.notifyPlayer(player,"你要找的物品已经不存在");
            return;
        }
        if (price < auctionItem.getAuctionPrice()) {
            notificationManager.notifyPlayer(player,"你出的价钱低于当前的竞拍价");
            return;
        }
        if (player.getMoney() < price) {
            notificationManager.notifyPlayer(player,"你身上的钱不足以支付");
            return;
        }
        ThingInfo thingInfo = thingInfoService.getThingInfo(auctionItem.getThingInfoId());
        Item item = new Item(thingInfoService.generateItemId(),
                auctionItem.getNumber(),thingInfo);

        // 如果是一口价模式
       if (auctionItem.getAuctionMode().equals(AuctionMode.SHELL_NOW.getType())
               && bagsService.addItem(player,item)) {
           player.moneyChange(-auctionItem.getAuctionPrice());
           notificationManager.notifyPlayer(player,"恭喜你，你已经获得了该物品");
           mailService.sendMail(DefaultSender.AuctionHouse.getId(),player.getId(),"物品出售时间已到",
                   MessageFormat.format("物品 {0} x {1}",thingInfo.getName(),auctionItem.getNumber())
                   ,item);
           auctionHouseManager.removeAuctionItem(auctionItem.getAuctionId());
       } else {
            // 竞拍模式
           bidAuctionItem(player,auctionItem,price);
        }
    }


    /**
     *  竞拍物品
     * @param player 玩家
     * @param auctionItem  拍卖品
     * @param price 出价
     */
    private synchronized void bidAuctionItem(Player player, AuctionItem auctionItem, int price) {
        Integer auctionMoney = auctionItem.getBiddersMap().get(player.getId());
        auctionItem.addBidder(player,price);
        auctionItem.setAuctionPrice(price);
        if (Objects.isNull(auctionMoney)) {
            //  第一次竞拍
            player.moneyChange(-price);
        } else {
            // 减去继续竞的差价
            player.moneyChange(-(price-auctionItem.getAuctionPrice()));
        }
        auctionHouseManager.updateAuctionItem(auctionItem);
        notificationManager.notifyPlayer(player,MessageFormat.format("叫价功成。当前拍卖品价格是{0}",
                auctionItem.getAuctionPrice()));
    }


    /**
     *  获取所有拍卖品
     */
    public Map<Integer, AuctionItem> getALLAuction() {
        return AuctionHouseManager.getAllAuctionItem();
    }


    /**
     *  结束竞拍
     */
    public void finishAuction(AuctionItem auctionItem) {
        Map<Long,Integer> biddersMap  = auctionItem.getBiddersMap();
        ThingInfo thingInfo = thingInfoService.getThingInfo(auctionItem.getThingInfoId());
        Item item = new Item(thingInfoService.generateItemId(),auctionItem.getNumber(),thingInfo);
        // 如果无人拍卖
        if (biddersMap.isEmpty()) {
            Player publisher = playerDataService.getPlayerById(auctionItem.getPublisherId());
            notificationManager.notifyPlayer(publisher,"物品发布时间结束，已发往你的邮箱，请注意查收");
            mailService.sendMail(DefaultSender.AuctionHouse.getId(),publisher.getId(),"物品出售时间已到",
                    MessageFormat.format("物品 {0} x {1}",thingInfo.getName(),auctionItem.getNumber())
            ,item);
        }
        // 价高者胜
        biddersMap.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .ifPresent(
                        entry -> {
                            Player winner = playerDataService.getOnlinePlayerById(entry.getKey());
                            notificationManager.notifyPlayer(winner,"恭喜你，你竞拍成功了，拍卖品已发往你的邮箱，请注意查收");
                            mailService.sendMail(DefaultSender.AuctionHouse.getId(),winner.getId(),"恭喜你，你竞拍成功了",
                                    MessageFormat.format("物品 {0} x {1}",thingInfo.getName(),auctionItem.getNumber())
                                    ,item);
                        }
                );
        auctionHouseManager.removeAuctionItem(auctionItem.getAuctionId());
    }
}
