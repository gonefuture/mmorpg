package com.wan37.gameserver.game.trade.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.game.trade.model.AuctionItem;
import com.wan37.gameserver.game.trade.service.AuctionHouseService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 19:47
 * @version 1.00
 * Description:
 */


@Controller
@Slf4j
public class AuctionHouseController {
    {
        ControllerManager.add(MsgId.AUCTION_BID,this::bid);
        ControllerManager.add(MsgId.AUCTION_PUSH,this::pushAuction);
        ControllerManager.add(MsgId.AUCTION_SHOW,this::showAuction);
    }



    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private AuctionHouseService auctionHouseService;

    @Resource
    private ThingInfoService thingInfoService;

    private void bid(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,3);
        Integer auctionId = Integer.valueOf(args[1]);
        Integer price = Integer.valueOf(args[2]);
        Player player = playerDataService.getPlayer(ctx);
        auctionHouseService.bid(player,auctionId,price);
    }


    private void pushAuction(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,4);
        Integer bagIndex = Integer.valueOf(args[1]);
        Integer auctionMode = Integer.valueOf(args[2]);
        Integer auctionPrice = Integer.valueOf(args[3]);
        Player player = playerDataService.getPlayer(ctx);
        auctionHouseService.pushAuction(player,
                bagIndex,
                auctionMode,
                auctionPrice
                );
    }


    private void showAuction(ChannelHandlerContext ctx, Message message) {
        Map<Integer, AuctionItem> auctionItemMap = auctionHouseService.getALLAuction();
        log.debug(" 拍卖品里列表 {}",auctionItemMap);
        NotificationManager.notifyByCtx(ctx,"拍卖行的拍卖品列表");
        StringBuilder sb = new StringBuilder();
        auctionItemMap.values().forEach(
                item -> sb.append(MessageFormat.format("物品{0} {1}  当前拍卖价{2} \n",
                        item.getAuctionId(),
                        thingInfoService.getThingInfo(item.getThingInfoId()).getName(),
                        item.getAuctionPrice())
                )
        );
        NotificationManager.notifyByCtx(ctx,sb.toString());
    }

}
