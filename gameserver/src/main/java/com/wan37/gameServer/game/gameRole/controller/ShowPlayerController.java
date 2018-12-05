package com.wan37.gameServer.game.gameRole.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/30 15:54
 * @version 1.00
 * Description: 展示玩家各种信息
 */

@Controller
public class ShowPlayerController implements IController {

    @Resource
    private PlayerDataService playerDataService;



    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayerByCtx(ctx);

        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("玩家 {0}，hp:{1}, mp:{2} 经验：{3} 战力： {4}  货币： {5}\n",
                player.getName(),player.getHp(),player.getMp(),player.getExp(), player.getAttack(),player.getMoney()));
        sb.append("玩家的属性是：\n");
        player.getRolePropertyMap().values().forEach(
                roleProperty -> {
                    sb.append(MessageFormat.format("属性: {0} : {1} , 描述：{2} \n",
                            roleProperty.getName(),roleProperty.getValue(),roleProperty.getDescribe()));
                }
        );
        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
