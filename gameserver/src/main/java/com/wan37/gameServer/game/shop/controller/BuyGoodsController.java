package com.wan37.gameServer.game.shop.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.shop.service.ShopService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 10:39
 * @version 1.00
 * Description: 购买货物服务
 */

@Controller
public class BuyGoodsController implements IController {

    @Resource
    private ShopService shopService;

    @Resource
    private PlayerDataService playerDataService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Integer goodsId = Integer.valueOf(cmd[1]);

        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = shopService.buyGoods(player,1,goodsId);

        message.setFlag((byte)1);
        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);
    }
}
