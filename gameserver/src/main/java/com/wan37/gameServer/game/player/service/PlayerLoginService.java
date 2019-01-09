package com.wan37.gameServer.game.player.service;


import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.model.User;
import com.wan37.gameServer.game.player.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.user.manager.UserCacheManger;
import com.wan37.gameServer.game.user.service.UserService;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PlayerLoginService {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private TPlayerMapper tPlayerMapper;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private UserCacheManger userCacheManger;


    @Resource
    private UserService userService;




    /**
     *  角色登陆
     */
    public Player login(Long playerId, ChannelHandlerContext ctx) {
        Player playerCache  = playerCacheMgr.getPlayerByCtx(ctx);

        // 如果角色缓存为空 或者 缓存中的角色不是要加载的角色，那就从数据库查询
        if (playerCache == null || !playerCache.getId().equals(playerId)) {
            TPlayer tPlayer=  tPlayerMapper.selectByPrimaryKey(playerId);
            Player player = new Player();
            BeanUtils.copyProperties(tPlayer,player);

            // 玩家初始化
            playerDataService.initPlayer(player);

            // 以channel id 为键储存玩家数据
            playerCacheMgr.putCtxPlayer(ctx,player);
            // 保存playerId跟ChannelHandlerContext之间的关系
            playerCacheMgr.savePlayerCtx(playerId,ctx);

            player.setCtx(ctx);

            return player;
        } else {
            // 以 当前的channelId缓存player
            playerCacheMgr.putCtxPlayer(ctx,playerCache);

            // 保存playerId跟ChannelHandlerContext之间的关系
            playerCacheMgr.savePlayerCtx(playerId,ctx);
            // 玩家初始化
            playerDataService.initPlayer(playerCache);

            return playerCache;
        }
    }


    /**
     *  判断用户是否拥有这个角色
     * @param ctx   上下文
     * @param playerId  要判定的角色id
     * @return 用户是否拥有此角色
     */
    public boolean hasPlayer(ChannelHandlerContext ctx, Long playerId) {
        User user = UserCacheManger.getUserByCtx(ctx);
        List<TPlayer>  tPlayerList = userService.findPlayers(user.getId());
        for (TPlayer tPlayer : tPlayerList) {
            if (tPlayer.getId().equals(playerId)) {
                log.debug("tPlayerId {0}   playerId {1} ",tPlayer.getId(),playerId);
                return true;
            }
        }
        return  false;
    }
}
