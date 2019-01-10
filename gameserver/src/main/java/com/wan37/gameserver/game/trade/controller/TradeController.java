package com.wan37.gameserver.game.trade.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.trade.service.TradeService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 12:15
 * @version 1.00
 * Description: 被邀请交易者同意开始交易
 */

@Controller
public class TradeController{

    {
        ControllerManager.add(MsgId.INITIATE_TRADE,this::tradeInitiate);
        ControllerManager.add(MsgId.BEGIN_TRADE,this::tradeBegin);
        ControllerManager.add(MsgId.TRADE_MONEY,this::tradeMoney);
        ControllerManager.add(MsgId.TRADE_GOODS,this::tradeGoods);
        ControllerManager.add(MsgId.CONFIRM_TRADE,this::tradeConfirm);
    }

    @Resource
    private TradeService tradeService;

    private void tradeBegin(ChannelHandlerContext ctx, Message message) {
            tradeService.beginTrade(ctx);
    }

    private void tradeInitiate(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long targetId = Long.valueOf(args[1]);

        tradeService.initiateTrade(ctx,targetId);
    }


    private void tradeMoney(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer moneyNumber = Integer.valueOf(args[1]);

        tradeService.tradeMoney(ctx,moneyNumber);
    }


    private void tradeGoods(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer itemId = Integer.valueOf(args[1]);
        tradeService.tradeGoods(ctx,itemId);
    }


    private void tradeConfirm(ChannelHandlerContext ctx, Message message) {
        tradeService.confirmTrade(ctx);
    }
}
