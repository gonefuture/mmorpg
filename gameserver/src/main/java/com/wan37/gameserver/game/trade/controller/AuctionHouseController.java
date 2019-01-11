package com.wan37.gameserver.game.trade.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.trade.service.AuctionHouseService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 19:47
 * @version 1.00
 * Description:
 */


@Controller
public class AuctionHouseController {
    {
        ControllerManager.add(MsgId.AUCTION_BID,this::bid);
    }

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private AuctionHouseService auctionHouseService;

    private void bid(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer auctionId = Integer.valueOf(args[1]);
        Integer price = Integer.valueOf(args[2]);
        Player player = playerDataService.getPlayer(ctx);
        auctionHouseService.bid(player,auctionId,price);
    }


    private void pushAuction(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,4);
        Integer bagIndex = Integer.valueOf(args[2]);
        Integer auctionMode = Integer.valueOf(args[3]);
        Integer auctionPrice = Integer.valueOf(args[4]);
        Player player = playerDataService.getPlayer(ctx);
        auctionHouseService.pushAuction(player,
                bagIndex,
                auctionMode,
                auctionPrice
                );
    }

}
