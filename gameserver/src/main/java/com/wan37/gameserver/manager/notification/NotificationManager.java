package com.wan37.gameserver.manager.notification;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.player.manager.PlayerCacheMgr;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.model.Creature;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 12:05
 * @version 1.00
 * Description: 通知管理器
 */

@Slf4j
@Service
public  class NotificationManager {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private GameSceneService gameSceneService;


    /**
     *  通知场景里的玩家
     * @param gameScene 场景
     * @param e 信息
     * @param <E> 信息的类型
     */
    public <E> void notifyScene(GameScene gameScene, E e) {
        Message message = new Message();
        if (e instanceof String || e instanceof StringBuilder) {
            message.setContent(e.toString());
        } else {
            message.setContent(JSON.toJSONString(e));
        }
        gameScene.getPlayers().keySet().forEach( playerId -> {
            ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(playerId);
            ctx.writeAndFlush(message.toProto());
        });
    }


    /**
     *  通知场景活物
     * @param creature 活物
     * @param e 信息
     * @param <E> 信息类型
     */
    public <E> void notifyCreature(Creature creature, E e) {
        if (creature instanceof Player) {
            notifyPlayer((Player) creature,e);
        }
    }

    /**
     *  通知单个玩家
     * @param player 玩家
     * @param e 信息
     * @param <E> 信息的类型
     */
    public <E> void notifyPlayer(Player player, E e) {
        Message message = new Message();
        message.setContent(e.toString());
        ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(player.getId());
        Optional.ofNullable(ctx).ifPresent(c -> c.writeAndFlush(message.toProto()));
    }




    /**
     *  通知单个玩家
     * @param players  玩家数组
     * @param e 信息
     * @param <E> 信息类型
     */
    public <E> void notifyPlayers(List<Player> players, E e) {
        Message message = new Message();
        message.setContent(e.toString());
        players.forEach(
                player -> {
                    ChannelHandlerContext ctx = playerCacheMgr.getCxtByPlayerId(player.getId());
                    ctx.writeAndFlush(message.toProto());
                }
        );
    }



    /**
     *  通过通道上下文来通知玩家
     * @param ctx 上下文
     * @param e 信息
     * @param <E> 信息的类型
     */
    public static  <E> void notifyByCtx(ChannelHandlerContext ctx,E e) {
        Message message = new Message();
        message.setContent((e.toString()+"\n"));
        ctx.writeAndFlush(message.toProto());
    }


    /**
     *  通知到家收到了另一个玩家的攻击
     * @param form 攻击发起者
     * @param to    攻击承受者
     * @param damage    伤害
     */
    public void playerBeAttacked(Player form , Player to, long damage) {
        GameScene gameScene = gameSceneService.getSceneByPlayer(form);
        notifyScene(gameScene,
                MessageFormat.format("\n玩家 {0} 受到 {1} 的攻击，  hp减少{2},当前hp为 {3}\n",
                        to.getName(),form.getName(),damage, to.getHp()));
    }


    /**
     *   玩家死亡通知
     * @param murderer 杀死玩家的生物
     * @param player 玩家
     */
    public void playerDead(Creature murderer, Player player) {
        GameScene gameScene = gameSceneService.getSceneByPlayer(player);
        notifyScene(gameScene,
                MessageFormat.format("玩家 {0} 被 {1} 杀死  \n",
                        player.getName(),murderer.getName()));

        notifyPlayer(player, "你已经死亡，正在传送墓地 \n");

    }


    public void playerBeHealed(Player form, Player to, Long heal) {
        GameScene gameScene = gameSceneService.getSceneByPlayer(form);
        notifyScene(gameScene,
                MessageFormat.format("\n玩家 {0} 受到 {1} 的治疗，  hp减增加{2},当前hp为 {3}\n",
                        to.getName(),form.getName(),heal, to.getHp()));
    }
}
