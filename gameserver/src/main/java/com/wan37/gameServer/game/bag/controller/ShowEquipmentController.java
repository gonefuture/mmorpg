package com.wan37.gameServer.game.bag.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.model.EquipmentBar;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/29 16:49
 * @version 1.00
 * Description: 展示装备
 */

@Controller
@Slf4j
public class ShowEquipmentController implements IController {

    @Resource
    private PlayerDataService playerDataService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        EquipmentBar equipmentBar = playerDataService.getPlayerByCtx(ctx).getEquipmentBar();
        StringBuilder sb = new StringBuilder();
        log.debug("展示武器栏 {}",equipmentBar );
        equipmentBar.getEquipmentMap().values().stream().
                map(Item::getThings).
                forEach(
                        things -> {
                            sb.append(MessageFormat.format("位置:{0} 装备: {1}   属性： ",
                                    things.getPart(),things.getName())
                            );

                            things.getThingRoleProperty().forEach(
                                    roleProperty -> {
                                        sb.append(MessageFormat.format(" {0}：{1} ",
                                                roleProperty.getName(),roleProperty.getCurrentValue()));
                                    }
                            );

                        sb.append("\n");
                        }
                );


        if (equipmentBar.getEquipmentMap().isEmpty())
            sb.append("你没有穿戴装备，快去商城或背包里挑一件吧");
        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
