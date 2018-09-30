package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 17:36
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class PlayerQuitService  {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    /**
     *  注销角色
     */
    public void logout(ChannelHandlerContext ctx) {
        cleanPlayerCache(ctx);

        ctx.close();
    }


    public void cleanPlayerCache(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        Player player = playerCacheMgr.get(channelId);
        // 移除角色所有的缓存
        playerCacheMgr.removePlayerCxt(player.getId());
        playerCacheMgr.removePlayerByChannelId(channelId);
    }


}
