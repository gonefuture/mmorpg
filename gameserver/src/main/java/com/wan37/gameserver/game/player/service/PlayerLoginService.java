package com.wan37.gameserver.game.player.service;


import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.scene.model.CommonSceneId;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.model.User;
import com.wan37.gameserver.game.player.manager.PlayerCacheMgr;
import com.wan37.gameserver.game.user.manager.UserCacheManger;
import com.wan37.gameserver.game.user.service.UserService;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.entity.TPlayerExample;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
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
    private UserService userService;

    @Resource
    private PlayerQuitService playerQuitService;



    /**
     *  角色登陆
     */
    public Player login(Long playerId, ChannelHandlerContext ctx) {
        Player playerCache  = playerCacheMgr.getPlayerByCtx(ctx);
        // 清理当前通道的角色
        playerQuitService.logoutScene(ctx);

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
                return true;
            }
        }
        return  false;
    }

    /**
     *  创建新角色
     * @param roleName  角色名字，数据路唯一
     * @param roleClass   职业
     * @param userId    用户id
     */
    public void roleCreate(ChannelHandlerContext ctx, String roleName, Integer roleClass, Long userId) {
        TPlayer tPlayer = new TPlayer();
        tPlayer.setName(roleName);
        tPlayer.setRoleClass(roleClass);
        tPlayer.setUserId(userId);
        tPlayer.setScene(CommonSceneId.BEGIN_SCENE.getId());

        try {
            tPlayerMapper.insertSelective(tPlayer);
        } catch( DuplicateKeyException e) {
            NotificationManager.notifyByCtx(ctx,"角色名已经存在");
            return;
        }
        TPlayerExample tPlayerExample = new TPlayerExample();
        tPlayerExample.or().andNameEqualTo(roleName);
        List<TPlayer> tPlayerList = tPlayerMapper.selectByExample(tPlayerExample);
        if (tPlayerList.size() > 0) {
            TPlayer newPlayer = tPlayerList.get(0);
            NotificationManager.notifyByCtx(ctx, MessageFormat.format("角色创建成功，登陆命id是{0}，名字是{1}",
                    newPlayer.getId(),newPlayer.getName()));
        }else {
            NotificationManager.notifyByCtx(ctx,"角色创建失败");
        }

    }
}
