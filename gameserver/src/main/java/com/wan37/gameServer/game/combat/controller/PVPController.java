package com.wan37.gameServer.game.combat.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 18:16
 * @version 1.00
 * Description: 玩家pvp普通攻击
 */

@Controller
public class PVPController implements IController {
    @Resource
    private CombatService combatService;

    @Resource
    private PlayerDataService playerDataService;



    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Long targetId = Long.valueOf(command[1]);

        Player player = playerDataService.getPlayerByCtx(ctx);

        Msg msg = combatService.commonAttackByPVP(player,targetId);

        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);

    }
}
