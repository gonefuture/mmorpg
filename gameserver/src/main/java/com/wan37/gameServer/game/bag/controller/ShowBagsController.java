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
import java.util.List;
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

        if (0 == itemMap.size() )
            sb.append("背包空荡荡的");
        for (Item item : itemMap.values()) {
            sb.append(MessageFormat.format("{0}  数量：{1} 描述：{2}  属性：",
                    item.getThings().getName(), item.getCount() ,item.getThings().getDescribe()));
            Set<RoleProperty> rolePropertyList = item.getThings().getThingRoleProperty();
            rolePropertyList.forEach(
                    roleProperty -> {
                        sb.append(MessageFormat.format("{0}:{1} "
                                ,roleProperty.getName(),roleProperty.getCurrentValue()));
                    }
            );
            sb.append("\n");
        }

        message.setFlag((byte) 1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
