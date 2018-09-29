package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.manager.cache.SceneManager;
import com.wan37.gameServer.service.PlayerMoveService;
import com.wan37.mysql.pojo.entity.TScene;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 15:51
 * @version 1.00
 * Description: JavaLearn
 */

@Slf4j
@Component
public class PlayerMoveController implements IController {

    @Resource
    private PlayerMoveService playerMoveService;

    @Resource
    private SceneManager sceneManager;

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        int sceneId =  Integer.valueOf(array[1]);

        TScene tScene = sceneManager.get(sceneId);
        String result = null;
        if (playerMoveService.moveScene(ctx.channel().id().asLongText(),sceneId)) {
            // 获取当前角色所在的场景

            result = ("你所在的地方是： "+tScene.toString());
        } else {
            result = "这个地点不能到"+tScene.toString();
        }

        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}

