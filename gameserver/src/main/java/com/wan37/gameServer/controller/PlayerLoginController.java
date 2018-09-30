package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.Player;
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

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        Long playerId = Long.valueOf(array[1]);
        String result = null;
        String cacheId =ctx.channel().id().asLongText();

        if (userLoginService.isUserOnline(cacheId) ){
            Player player = playerLoginService.login(playerId,cacheId);
            TScene tScene = playerMoveService.currentScene(cacheId);
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
