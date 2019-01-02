package com.wan37.gameServer.game.gameRole.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.gameRole.manager.RoleClassManager;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.model.RoleClass;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/21 17:41
 * @version 1.00
 * Description: 玩家控制器
 */

@Controller
public class PlayerController {

    {
        ControllerManager.add(MsgId.SHOW_PLAYER,this::showPlayer);
    }



    @Resource
    private PlayerDataService playerDataService;



    private void showPlayer(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayerByCtx(ctx);

        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("玩家 {0}，hp:{1}, mp:{2} 经验：{3} 战力： {4}  等级：{5} 货币： {6}\n",
                player.getName(),player.getHp(),player.getMp(),player.getExp(),
                player.getAttack(),player.getLevel(),player.getMoney()));
        sb.append("玩家正在冷却的技能:\n");
        if (player.getHasUseSkillMap().isEmpty()) {
            sb.append("无");
        } else {
            player.getHasUseSkillMap().values().forEach(
                    skill -> {
                        sb.append(MessageFormat.format("技能： {0} 等级：{1} 耗蓝:{2} cd:{3}  冷却完成时间还剩:{4}秒 \n",
                                skill.getName(),skill.getLevel(), skill.getMpConsumption(), skill.getCd(),
                                (skill.getCd() - (System.currentTimeMillis()-skill.getActiveTime()))*0.001
                        ));
                    }
            );
        }
        sb.append("\n");
        sb.append("身上带有的buffer：\n");
        if (player.getBufferList().isEmpty()) {
            sb.append("无\n");
        } else {
            player.getBufferList().forEach(
                    buffer -> {
                        sb.append(MessageFormat.format("状态： {0} 效果：hp:{1} mp:{2}  每:{3}秒触发一次  剩余:{4}秒 \n",
                                buffer.getName(),buffer.getHp(), buffer.getMp(),
                                (buffer.getIntervalTime()*0.001),
                                (buffer.getDuration()- (System.currentTimeMillis()-buffer.getStartTime()))*0.001
                        ));
                    }
            );
        }

        sb.append("\n");
        sb.append("玩家的属性是：\n");
        player.getRolePropertyMap().values().forEach(
                roleProperty -> {
                    sb.append(MessageFormat.format("属性: {0} : {1} , 描述：{2} \n",
                            roleProperty.getName(),roleProperty.getValue(),
                            Optional.ofNullable(roleProperty.getDescribe()).orElse("无")));
                }
        );
        RoleClass roleClass= RoleClassManager.getRoleClass(player.getRoleClass());
        sb.append(MessageFormat.format("玩家的职业是{0} ：\n",roleClass.getName()));
        sb.append("拥有技能：\n");
        roleClass.getSkillMap().values().forEach( skill ->
                sb.append(MessageFormat.format("技能id：{0} 名字：{1} 伤害：{2} 消耗：{3} cd：{4} 描述：{5}  \n" ,
                        skill.getId(),skill.getName(),skill.getHpLose(),skill.getMpConsumption(),
                        skill.getCd(),skill.getDescribe())));


        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }





}
