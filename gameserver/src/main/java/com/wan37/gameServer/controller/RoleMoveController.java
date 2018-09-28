package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.manager.cache.SceneManager;
import com.wan37.gameServer.service.RoleMoveService;
import com.wan37.mysql.pojo.entity.Scene;
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
public class RoleMoveController  implements IController {

    @Resource
    private RoleMoveService roleMoveService;

    @Resource
    private SceneManager sceneManager;

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        long roleId = Long.valueOf(array[1]);
        int sceneId =  Integer.valueOf(array[2]);
        roleMoveService.moveScene(roleId,sceneId);
        // 获取当前角色所在的场景
        Scene scene = sceneManager.get(sceneId);
        message.setContent(("你所在的地方是： "+scene.toString()).getBytes());
        ctx.writeAndFlush(message);
    }
}

