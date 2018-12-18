package com.wan37.gameServer.game.trade.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.trade.service.TradeService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 15:32
 * @version 1.00
 * Description: 确认交易
 */

@Controller
public class ConfirmTradeController implements IController {
    {
        ControllerManager.add(MsgId.CONFIRM_TRADE,this);
    }

    @Resource
    private TradeService tradeService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        tradeService.confirmTrade(ctx);
    }
}
