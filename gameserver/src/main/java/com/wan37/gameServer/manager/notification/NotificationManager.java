package com.wan37.gameServer.manager.notification;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.GameScene;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 12:05
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Service
public class NotificationManager {


    @Resource
    private PlayerCacheMgr playerCacheMgr;


    public void notifyScenePlayer(GameScene gameScene,Message message) {
        gameScene.getPlayers().keySet().forEach( playerId-> {
            ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(playerId);
            ctx.writeAndFlush(message);
        });

    }


    public <E> void notifyScenePlayerWithMessage(GameScene gameScene, E e) {
        Message message = new Message();
        message.setFlag((byte) 1);
        message.setContent(JSON.toJSONString(e).getBytes());
        notifyScenePlayer(gameScene,message);
    }

}
