package com.wan37.gameServer.game.friend.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.friend.service.FriendService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 18:24
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class FriendController {

    {
        ControllerManager.add(MsgId.FRIEND_LIST,this::friendList);
        ControllerManager.add(MsgId.FRIEND_ADD,this::friendAdd);
    }


    @Resource
    private FriendService friendService;

    @Resource
    private PlayerDataService playerDataService;




    private void friendAdd(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long friendId = Long.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        friendService.friendAdd(player,friendId);
    }

    private void friendList(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        friendService.friendList(player);
    }


}
