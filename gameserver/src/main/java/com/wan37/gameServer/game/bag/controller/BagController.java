package com.wan37.gameServer.game.bag.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/28 15:05
 * @version 1.00
 * Description: 背包相关
 */


@Controller
@Slf4j
public class BagController  {
    {
        ControllerManager.add(MsgId.PACK_BAG,this::packBag);
        ControllerManager.add(MsgId.SHOW_BAGS,this::showBag);
    }

    @Resource
    private BagsService bagsService;

    @Resource
    private PlayerDataService playerDataService;


    public void packBag(ChannelHandlerContext ctx, Message message) {
        bagsService.packBag(ctx);
    }



    public void showBag(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayerByCtx(ctx);
        Map<Integer, Item> itemMap = bagsService.show(player);
        log.debug("itemMap {}",itemMap);

        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("背包：{0} 大小: {1}",
                player.getBag().getBagName(),player.getBag().getBagSize())).append("\n");

        if (0 == itemMap.size() )
            sb.append("背包空荡荡的");
        for (Map.Entry<Integer,Item> entry : itemMap.entrySet()) {
            sb.append(MessageFormat.format("格子：{0}  {1} {2} 数量：{3} 描述：{4}  属性：",
                    entry.getKey(), entry.getValue().getThings().getName(), entry.getValue().getThings().getPart(),
                    entry.getValue().getCount() ,entry.getValue().getThings().getDescribe()));
            // 遍历物品属性
            Set<RoleProperty> rolePropertyList = entry.getValue().getThings().getThingRoleProperty();
            rolePropertyList.forEach(
                    roleProperty -> sb.append(MessageFormat.format("{0}:{1} "
                                ,roleProperty.getName(),roleProperty.getThingPropertyValue()))
            );
            sb.append(MessageFormat.format(" 等级：{0}，价格：{1}",
                    Optional.ofNullable(entry.getValue().getThings().getLevel()).orElse(0),
                    Optional.ofNullable(entry.getValue().getThings().getPrice()).orElse(0)));
            sb.append("\n");
        }

        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
