package com.wan37.gameServer.game.trade.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.trade.service.TradeService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 15:31
 * @version 1.00
 * Description: 交易金币
 */
public class TradeMoneyController implements IController {

    {
        ControllerManager.add(MsgId.TRADE_MONEY,this);
    }


    @Resource
    private TradeService tradeService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] args =  new String(message.getContent()).split("\\s+");
        Long moneyNumber = Long.valueOf(args[1]);

        tradeService.tradeMoney(ctx,moneyNumber);
    }
}
