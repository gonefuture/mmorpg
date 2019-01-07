package com.wan37.gameServer.game.player.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 17:36
 * @version 1.00
 * Description: 角色退出服务
 */
@Service
public class PlayerQuitService  {



    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private TPlayerMapper tPlayerMapper;

    @Resource
    private BagsService bagsService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private PlayerDataService playerDataService;


    @Resource
    private GameSceneService gameSceneService;

    /**
     *  主动注销当前角色
     */
    public void logout(ChannelHandlerContext ctx) {

        // 从场景退出
        logoutScene(ctx);

        // 主动退出游戏的清除其缓存
        //cleanPlayerCache(ctx);

    }


    /**
     *  退出场景
     */

    public void logoutScene(ChannelHandlerContext ctx) {
        Player player =playerDataService.getPlayerByCtx(ctx);
        GameScene gameScene = gameSceneService.findSceneByCtx(ctx);
        notificationManager.<String>notifyScene(gameScene,
                MessageFormat.format("玩家 {0} 正在退出", player.getName()));
        // 重点，从场景中移除
        gameScene.getPlayers().remove(player.getId());
    }






    /**
     *  清除与角色相关的缓存
     */
    public void cleanPlayerCache(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();

        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player != null) {
            // 移除角色所有的缓存
            playerCacheMgr.removePlayerCxt(player.getId());
            playerCacheMgr.removePlayerByChannelId(channelId);
        }
    }


    /**
     *  保存角色所有数据
     */
    public void savePlayer(ChannelHandlerContext ctx) {
        Player player = playerCacheMgr.getPlayerByCtx(ctx);

        // 保存角色信息
        if (player != null) {
            // 持久化角色信息
            TPlayer tPlayer = new TPlayer();
            BeanUtils.copyProperties(player,tPlayer);
            // 保存背包
            tPlayer.setEquipments(JSON.toJSONString(player.getEquipmentBar()));

            tPlayerMapper.updateByPrimaryKey(tPlayer);
            // 保存角色背包
            bagsService.saveBag(player);
        }
    }


}
