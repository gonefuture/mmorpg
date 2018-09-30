package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.mapper.TPlayerMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


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


    /**
     *  角色登陆
     */
    public Player login(Long playerId, String channelId) {
        Player playerCache  = playerCacheMgr.get(channelId);

        if (playerCache == null) {
            TPlayer tPlayer=  tPlayerMapper.selectByPrimaryKey(playerId);
            Player player = new Player();
            BeanUtils.copyProperties(tPlayer,player);
            // 以channel id 为键储存玩家数据
            playerCacheMgr.put( channelId,player);
            // 保存playerId跟channelId之间的关系
            playerCacheMgr.savePlayerId(playerId,channelId);

            return player;
        } else {
            return playerCache;
        }
    }

}
