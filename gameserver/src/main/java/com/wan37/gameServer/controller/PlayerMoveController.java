package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.manager.cache.SceneCacheMgr;
import com.wan37.gameServer.service.PlayerMoveService;
import com.wan37.mysql.pojo.entity.TScene;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


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
    private SceneCacheMgr sceneCacheMgr;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        int willMoveSceneId =  Integer.valueOf(array[1]);

        GameScene gameScene = sceneCacheMgr.get(willMoveSceneId);
        Map<String, Object> result = new HashMap<>();
        if (playerMoveService.moveScene(ctx.channel().id().asLongText(),willMoveSceneId)) {
            // 获取当前角色所在的场景

            result.put("你所在的地方是： ",gameScene.toString());
        } else {
            result.put("这个地点不能到： ",gameScene.toString());
            GameScene currentScene = playerMoveService.currentScene(ctx.channel().id().asLongText());
            result.put("\n 当前所处的地方是",currentScene);
        }

        message.setContent(JSON.toJSONString(result).getBytes());
        ctx.writeAndFlush(message);
    }
}
