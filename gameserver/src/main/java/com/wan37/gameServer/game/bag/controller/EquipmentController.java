package com.wan37.gameServer.game.bag.controller;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/29 9:11.
 *  说明：
 */

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.bag.service.EquipmentBarService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;

/**
 * <pre> </pre>
 */

@Controller
public class EquipmentController  {
    {
        ControllerManager.add(MsgId.SHOW_EQUIPMENT_BAR,this::showEquip);
        ControllerManager.add(MsgId.EQUIP,this::equip);
        ControllerManager.add(MsgId.REMOVE_EQUIP,this::removeEquip);

    }


    @Resource
    private EquipmentBarService equipmentBarService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private BagsService bagsService;


    public void equip(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Integer cellId = Integer.valueOf(command[1]);
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        Things things = bagsService.getThings(player,cellId);
        boolean flag = equipmentBarService.equip(player,cellId);


        if (flag) {
            message.setFlag((byte) 1);
            message.setContent(MessageFormat.format("装备 {0} 在 {1} 成功",things.getName() ,things.getPart()).getBytes());
        } else {
            message.setFlag((byte) -1);
            message.setContent("装备失败".getBytes());
        }
        ctx.writeAndFlush(message);
    }



    public void showEquip(ChannelHandlerContext ctx, Message message) {
        Map<String, Item> equipmentBar = playerDataService.getPlayerByCtx(ctx).getEquipmentBar();
        StringBuilder sb = new StringBuilder();
        equipmentBar.values().stream().
                map(Item::getThings).
                forEach(
                        things -> {
                            sb.append(MessageFormat.format("位置:{0} 装备: {1}   属性： ",
                                    things.getPart(),things.getName())
                            );

                            things.getThingRoleProperty().forEach(
                                    roleProperty -> {
                                        sb.append(MessageFormat.format(" {0}：{1} ",
                                                roleProperty.getName(),roleProperty.getThingPropertyValue()));
                                    }
                            );

                            sb.append("\n");
                        }
                );

        if (equipmentBar.isEmpty())
            sb.append("你没有穿戴装备，快去商城或背包里挑一件吧");
        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }




    public void removeEquip(ChannelHandlerContext ctx, Message message) {
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
