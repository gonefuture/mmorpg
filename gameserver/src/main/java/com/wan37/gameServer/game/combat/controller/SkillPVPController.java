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
import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 18:18
 * @version 1.00
 * Description: 使用技能进行PVP
 */

@Controller
public class SkillPVPController implements IController {

    @Resource
    private CombatService combatService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");

        Integer skillId = Integer.valueOf(command[1]);

        Long targetId = Long.valueOf(command[2]);

        String[] targetListString = Arrays.copyOfRange(command,2,command.length);


        Player player = playerDataService.getPlayerByCtx(ctx);


        List<Long>  targetIdList =  Arrays.stream(targetListString).map(Long::valueOf).collect(Collectors.toList());

        Msg msg = combatService.useSkillPVP(player,skillId,targetIdList);
        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);
    }
}
