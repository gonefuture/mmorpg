package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private TPlayerMapper tPlayerMapper;

    /**
     *  注销当前角色
     */
    public void logout(ChannelHandlerContext ctx) {
        cleanPlayerCache(ctx);
        ctx.close();
    }

    /**
     *  请除与角色相关的缓存
     */
    public void cleanPlayerCache(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        Player player = playerCacheMgr.get(channelId);
        if (player != null) {
            // 移除角色所有的缓存
            playerCacheMgr.removePlayerCxt(player.getId());
            playerCacheMgr.removePlayerByChannelId(channelId);
        }
    }


    /**
     *  保存角色信息
     */
    public void savePlayer(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        Player player = playerCacheMgr.get(channelId);

        if (player != null) {
            // 持久化角色信息
            TPlayer tPlayer = new TPlayer();
            BeanUtils.copyProperties(player,tPlayer);
            tPlayerMapper.updateByPrimaryKey(tPlayer);
        }
    }


}
