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
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

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

        message.setFlag((byte) 1);
        message.setContent(JSON.toJSONBytes(bagsCellList));
        ctx.writeAndFlush(message);
    }
}
