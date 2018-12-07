package com.wan37.gameServer.game.gameRole.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Optional;

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
        sb.append("玩家正在冷却的技能:\n");
       if (player.getSkillMap().isEmpty()) {
           sb.append("无\n");
       } else {
           player.getSkillMap().values().forEach(
                   skill -> {
                       sb.append(MessageFormat.format("技能： {0} 等级：{1} 耗蓝:{2} cd:{3}  冷却完成时间还剩:{4}秒",
                               skill.getName(),skill.getLevel(), skill.getMpConsumption(), skill.getCd(),
                               (skill.getActivetime() - skill.getCd())*1000
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
                        sb.append(MessageFormat.format("状态： {0} 效果：hp:{1} mp:{2}  每:{3}秒触发一次  剩余:{4}秒",
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
        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
