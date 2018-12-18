package com.wan37.gameServer.game.gameRole.service;


import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.model.User;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.gameServer.manager.cache.UserCacheMgr;
import com.wan37.gameServer.game.user.service.UserService;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 16:03
 * @version 1.00
 * Description: 角色登陆服务
 */
@Service
public class PlayerLoginService {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private TPlayerMapper tPlayerMapper;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private UserCacheMgr userCacheMgr;


    @Resource
    private UserService userService;




    /**
     *  角色登陆
     */
    public Player login(Long playerId, ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        Player playerCache  = playerCacheMgr.get(channelId);


        // 如果角色缓存为空 或者 不是相同的角色，那就从数据库查询
        if (playerCache == null || !playerCache.getId().equals(playerId)) {
            TPlayer tPlayer=  tPlayerMapper.selectByPrimaryKey(playerId);
            Player player = new Player();
            BeanUtils.copyProperties(tPlayer,player);

            // 玩家初始化
            playerDataService.initPlayer(player);

            // 以channel id 为键储存玩家数据
            playerCacheMgr.put(channelId,player);
            // 保存playerId跟ChannelHandlerContext之间的关系
            playerCacheMgr.savePlayerCtx(playerId,ctx);

            player.setCtx(ctx);

            return player;
        } else {
            // 以 当前的channelId缓存player
            playerCacheMgr.put(channelId,playerCache);

            // 保存playerId跟ChannelHandlerContext之间的关系
            playerCacheMgr.savePlayerCtx(playerId,ctx);

            return playerCache;
        }
    }


    /**
     *  判断用户是否拥有这个角色
     */

    public boolean hasPlayer(String channelId, Long playerId) {
        User user = userCacheMgr.get(channelId);
        List<TPlayer>  tPlayerList = userService.findPlayers(user.getId());
        for (TPlayer tPlayer : tPlayerList) {
            if (tPlayer.getId().equals(playerId)) {
                return true;
            }
        }
        return  false;
    }
}
