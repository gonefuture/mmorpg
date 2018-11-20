package com.wan37.gameServer.game.gameRole.controller;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/5 10:36.
 *  说明：
 */

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.BagsCell;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.BagsService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * <pre> </pre>
 */

@Controller
public class BagsController implements IController {

    @Resource
    private BagsService bagsService;


    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        //String[] command = new String(message.getContent()).split(" \\s+");

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        List<BagsCell> bagsCellList = bagsService.show(player.getId());

        StringBuilder sb = new StringBuilder();
        for (BagsCell bagsCell : bagsCellList) {
            sb.append(MessageFormat.format("{0}  数量：{1} 描述：{2}  属性：",
                    bagsCell.getThings().getName(), bagsCell.getTItem().getNumber() ,bagsCell.getThings().getDescribe()));
            Set<RoleProperty> rolePropertyList = bagsCell.getThings().getThingRoleProperty();
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
