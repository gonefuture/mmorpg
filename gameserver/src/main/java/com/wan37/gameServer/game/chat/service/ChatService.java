package com.wan37.gameServer.game.chat.service;

import com.google.common.cache.Cache;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.player.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 17:50
 * @version 1.00
 * Description: 聊天服务
 */

@Service
public class ChatService {

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private PlayerCacheMgr playerCacheMgr;


    /**
     *   私聊
     * @param player 私聊发起玩家
     * @param target  私聊目标
     * @param words 消息
     * @return  结果
     */
    public Msg whisper(Player player, long target , String words) {
        String message = MessageFormat.format("{0}悄悄对你说： {1}",
                player.getName(),words);
        Player targetPlayer = playerDataService.getOnlinePlayerById(target);
        if (null == targetPlayer )
            return new Msg(404,"没有发现玩家,或许其已下线");

        notificationManager.<String>notifyPlayer(targetPlayer,message);
        return new Msg(200,"发送信息成功");
    }


    /**
     *  公共聊天
     * @param words 消息
     */
    public void publicChat(Player player , String words) {
        Map<ChannelHandlerContext,Player> playerCache = playerCacheMgr.getAllPlayerCache();
        String message = MessageFormat.format("[全服]: {0} : {1}",
                player.getName(),words);

        playerCache.values().forEach(
                playerOnline -> {
                    notificationManager.<String>notifyPlayer(playerOnline,message);
                }
        );

    }
}
