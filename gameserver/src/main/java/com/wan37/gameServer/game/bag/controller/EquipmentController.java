package com.wan37.gameServer.game.bag.controller;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/29 9:11.
 *  说明：
 */

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.things.model.Things;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * <pre> </pre>
 */

@Controller
public class EquipmentController  implements IController {
    @Resource
    private EquipmentBarService equipmentBarService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private BagsService bagsService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Integer cellId = Integer.valueOf(command[1]);
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        Things things = bagsService.getThings(player,cellId);
        boolean flag = equipmentBarService.equip(player,cellId);


        if (flag) {
            message.setFlag((byte) 1);
            message.setContent(MessageFormat.format("装备 {0} 成功",things).getBytes());
        } else {
            message.setFlag((byte) -1);
            message.setContent("装备失败".getBytes());
        }
        ctx.writeAndFlush(message);
    }
}
