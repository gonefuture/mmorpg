package com.wan37.gameServer.manager.notification;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.model.Creature;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 12:05
 * @version 1.00
 * Description: 通知管理器
 */

@Slf4j
@Service
public class NotificationManager {

    @Resource
    private PlayerCacheMgr playerCacheMgr;


    @Resource
    private GameSceneService gameSceneService;




    public void notifyScenePlayer(GameScene gameScene,Message message) {
        gameScene.getPlayers().keySet().forEach( playerId-> {
            ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(playerId);
            ctx.writeAndFlush(message);
        });

    }


    public <E> void notifyScenePlayerWithMessage(GameScene gameScene, E e) {
        Message message = new Message();
        if (e instanceof String) {
            message.setContent(e.toString().getBytes());
        } else {
            message.setContent(JSON.toJSONString(e).getBytes());
        }
        message.setFlag((byte) 1);

        notifyScenePlayer(gameScene,message);
    }


    public <E> void notifyPlayer(Player player, E e) {
        Message message = new Message();
        message.setFlag((byte) 1);
        if (e instanceof String) {
            message.setContent(e.toString().getBytes());
        } else {
            message.setContent(JSON.toJSONString(e).getBytes());
        }
        ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(player.getId());
        ctx.writeAndFlush(message);
    }


    public <E> void notifyByCtx(ChannelHandlerContext ctx,E e) {
        Player player = playerCacheMgr.get(ctx.channel().id().asLongText());
        notifyPlayer(player,e);
    }



    public void playerBeAttacked(Player form , Player to, long damage) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(form);
        notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("\n玩家 {0} 受到 {1} 的攻击，  hp减少{2},当前hp为 {3}\n",
                        to.getName(),form.getName(),damage, to.getName()));
    }

    public void playerDead(Creature murderer, Player player) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家 {0} 被 {1} 杀死  \n",
                        player.getName(),murderer.getName()));

        notifyPlayer(player, "你已经死亡，正在传送墓地 \n");

    }





}
