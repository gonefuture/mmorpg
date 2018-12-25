package com.wan37.gameServer.game.combat.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;

import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.lang.reflect.Parameter;
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
        ControllerManager.add(MsgId.USE_SKILLS,this::useSkillAttackMonster);
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
}
