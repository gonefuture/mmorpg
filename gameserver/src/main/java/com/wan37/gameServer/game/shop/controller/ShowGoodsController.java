package com.wan37.gameServer.game.shop.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.shop.service.ShopService;
import com.wan37.gameServer.game.things.model.Things;
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
public class ShowGoodsController implements IController {

    @Resource
    private ShopService shopService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        // 此处假设只有一个商店
        Map<Integer,Things>  goodsMap = shopService.showGoods(1);
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("欢迎来到地精的{0}商店 \n","旅行 "));
        goodsMap.values().forEach(
                things -> {
                    sb.append(MessageFormat.format("id:{0}  货物：{1}  价格:{2} \n",
                            things.getId(),things.getName(),things.getPrice()
                    ));
                }
        );

        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
