package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.gameServer.service.PlayerLoginService;
import com.wan37.gameServer.service.PlayerMoveService;
import com.wan37.gameServer.service.UserLoginService;
import com.wan37.mysql.pojo.entity.TScene;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 17:09
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class PlayerLoginController implements IController {

    @Resource
    private PlayerLoginService playerLoginService;

    @Resource
    private PlayerMoveService playerMoveService;

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private PlayerCacheMgr playerCacheMgr;



    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        Long playerId = Long.valueOf(array[1]);
        String result = null;
        String channelId = ctx.channel().id().asLongText();
        if (userLoginService.isUserOnline(channelId) && playerLoginService.hasPlayer(channelId, playerId) ){
            Player player = playerLoginService.login(playerId,channelId);
            // 保存playerId跟ChannelHandlerContext之间的关系
            playerCacheMgr.savePlayerCtx(playerId,ctx);

            TScene tScene = playerMoveService.currentScene(channelId);
            // 将角色加入场景
            playerMoveService.putPlayerInScene(tScene,player);
            log.debug("player"+player);
            result = JSON.toJSON(player)
                    + "\n 你所在位置为"
                    +JSON.toJSON(tScene);
            message.setFlag((byte) 1);
        } else {
            result = "用户尚未登陆，不能加载角色";
            message.setFlag((byte) -1);
        }


        message.setContent(result.getBytes());
        log.debug("角色登陆返回的信息: "+ result);
        ctx.writeAndFlush(message);
    }
}
