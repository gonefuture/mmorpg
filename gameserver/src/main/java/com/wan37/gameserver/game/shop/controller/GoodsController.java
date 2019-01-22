package com.wan37.gameserver.game.shop.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.shop.service.ShopService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 15:17
 * @version 1.00
 * Description: 展示商店货物
 */

@Controller
public class GoodsController {


    {
        ControllerManager.add(Cmd.BUY_GOODS,this::buyGoods);
        ControllerManager.add(Cmd.SHOW_SHOP,this::showGoods);

    }



    @Resource
    private ShopService shopService;

    @Resource
    private PlayerDataService playerDataService;





    private void showGoods(ChannelHandlerContext ctx, Message message) {
        // 此处假设只有一个商店
        Map<Integer, ThingInfo>  goodsMap = shopService.showGoods(1);
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("欢迎来到地精的{0}商店 \n","旅行 "));
        goodsMap.values().forEach(
                things -> {
                    sb.append(MessageFormat.format("id:{0}  货物：{1}  价格:{2} \n",
                            things.getId(),things.getName(),things.getPrice()
                    ));
                }
        );

        NotificationManager.notifyByCtx(ctx,sb);
    }


    private void buyGoods(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Integer goodsId = Integer.valueOf(cmd[1]);

        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = shopService.buyGoods(player,1,goodsId);


        NotificationManager.notifyByCtx(ctx,msg.getMsg());
    }
}
