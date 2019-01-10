package com.wan37.gameserver.game.trade.service;

import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.game.trade.manager.AuctionHouseManager;
import com.wan37.gameserver.game.trade.model.AuctionItem;
import com.wan37.gameserver.game.trade.model.AuctionMode;
import com.wan37.gameserver.manager.notification.NotificationManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 18:08
 * @version 1.00
 * Description: 拍卖行服务
 */

@Service
public class AuctionHouseService  {

    @Resource
    private BagsService bagsService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private AuctionHouseManager auctionHouseManager;

    @Resource
    private ThingInfoService thingInfoService;


    public void pushAuction(Player player, Integer bagIndex,Integer auctionMode, Integer auctionPrice) {
        Item item = bagsService.removeItem(player,bagIndex);
        if (Objects.isNull(item)) {
            notificationManager.notifyPlayer(player,"你的背包这个格子并没有物品");
            return;
        }
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setNumber(item.getCount());
        auctionItem.setThingInfoId(item.getThingInfo().getId());
        auctionItem.setAuctionMode(auctionMode);
        auctionItem.setAuctionPrice(auctionPrice);
        auctionItem.setPushTime(new Date());
        auctionHouseManager.putAuctionItem(auctionItem);
    }



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
           return;
       }
       // 竞拍模式
       if (auctionItem.getAuctionMode().equals(AuctionMode.AT_AUCTION.getType())) {


        }

    }



    public void bidAuctionItem(Player player, AuctionItem auctionItem,int price) {
        Integer auctionMoney = auctionItem.getBiddersMap().get(player.getId());
        if (Objects.isNull(auctionMoney)) {
            //  第一次竞拍
            auctionItem.addBidder(player,price);
        } else {
            auctionItem.addBidder(player,price);
            // 减去继续竞的差价
            player.moneyChange(-(price-auctionItem.getAuctionPrice()));
        }


    }




}
