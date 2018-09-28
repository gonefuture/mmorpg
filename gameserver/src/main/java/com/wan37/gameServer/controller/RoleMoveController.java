package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.manager.CacheManager;
import com.wan37.gameServer.service.RoleMoveService;
import com.wan37.mysql.pojo.entity.Scene;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
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
    private RoleMoveService roleMoveSercice;


    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        long roleId = Long.valueOf(array[1]);
        int sceneId =  Integer.valueOf(array[2]);
        roleMoveSercice.moveScene(roleId,sceneId);
        // 获取当前角色所在的场景
        Scene scene = (Scene) CacheManager.get("scene:"+sceneId);
        message.setContent(("你所在的地方是： "+scene.toString()).getBytes());
        ctx.writeAndFlush(message);
    }
}

