package com.wan37.gameServer.game.bag.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/3 15:18
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class RemoveEquipController implements IController {

    @Resource
    private EquipmentBarService equipmentBarService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        // 需要卸下装备的部位
        String part = cmd[1];

        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = equipmentBarService.removeEquip(player,part);

        message.setFlag((byte) 1);
        message.setContent(msg.getMsg().getBytes());

        ctx.writeAndFlush(message);
    }
}
