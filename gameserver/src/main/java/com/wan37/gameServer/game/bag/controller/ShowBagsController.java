package com.wan37.gameServer.game.bag.controller;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/5 10:36.
 *  说明：
 */

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

/**
 * <pre> </pre>
 */

@Controller
@Slf4j
public class ShowBagsController implements IController {

    @Resource
    private BagsService bagsService;


    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        //String[] command = new String(message.getContent()).split(" \\s+");

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        Map<Integer,Item> itemMap = bagsService.show(player);
        log.debug("itemMap {}",itemMap);

        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format("背包：{0} 大小: {1}",
                player.getBag().getBagName(),player.getBag().getBagSize())).append("\n");

        if (0 == itemMap.size() )
            sb.append("背包空荡荡的");
        for (Map.Entry<Integer,Item> entry : itemMap.entrySet()) {
            sb.append(MessageFormat.format("格子：{0}  {1}  数量：{2} 描述：{3}  属性：",
                    entry.getKey(), entry.getValue().getThings().getName(), entry.getValue().getCount() ,entry.getValue().getThings().getDescribe()));
            // 遍历物品属性
            Set<RoleProperty> rolePropertyList = entry.getValue().getThings().getThingRoleProperty();
            rolePropertyList.forEach(
                    roleProperty -> {
                        sb.append(MessageFormat.format("{0}:{1} "
                                ,roleProperty.getName(),roleProperty.getThingPropertyValue()));
                    }
            );
            sb.append(MessageFormat.format(" 价格：{0}",entry.getValue().getThings().getPrice()));
            sb.append("\n");
        }

        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
