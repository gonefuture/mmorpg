package com.wan37.gameServer.game.combat.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;

import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 14:29
 * @version 1.00
 * Description: 玩家攻击怪物
 */


@Controller
public class AttackController {

    {
        ControllerManager.add(MsgId.COMMON_ATTACK,this::commonAttack);
        ControllerManager.add(MsgId.PVE_SKILL,this::useSkillAttackMonster);
        ControllerManager.add(MsgId.PVP,this::pvpAttack);
        ControllerManager.add(MsgId.SKILL_PVP,this::pvpBySkill);
    }


    @Resource
    private CombatService combatService;

    @Resource
    private PlayerDataService playerDataService;


    private void commonAttack(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Long gameObjectId = Long.valueOf(command[1]);

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
       combatService.commonAttack(player,gameObjectId);
    }




    private void useSkillAttackMonster(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Integer skillId = Integer.valueOf(command[1]);
        Long targetId = Long.valueOf(command[2]);
        List<Long> targetIdList = Arrays.stream(Arrays.copyOfRange(command,2,command.length))
                .map(Long::valueOf).collect(Collectors.toList());

        combatService.useSkillAttackMonster(ctx,skillId,targetIdList);
    }



    private void pvpAttack(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Long targetId = Long.valueOf(command[1]);

        Player player = playerDataService.getPlayerByCtx(ctx);

        combatService.commonAttackByPVP(player,targetId);
    }


    private void pvpBySkill(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");

        Integer skillId = Integer.valueOf(command[1]);

        Long targetId = Long.valueOf(command[2]);

        String[] targetListString = Arrays.copyOfRange(command,2,command.length);

        Player player = playerDataService.getPlayerByCtx(ctx);

        List<Long>  targetIdList =  Arrays.stream(targetListString).map(Long::valueOf).collect(Collectors.toList());

        combatService.useSkillPVP(player,skillId,targetIdList);
    }


}
