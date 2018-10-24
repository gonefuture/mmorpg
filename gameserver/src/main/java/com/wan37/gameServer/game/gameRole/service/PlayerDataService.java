package com.wan37.gameServer.game.gameRole.service;

import com.wan37.gameServer.game.gameRole.modle.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:07
 * @version 1.00
 * Description: mmorpg
 */
@Service
@Slf4j
public class PlayerDataService {


    @Resource
    private PlayerCacheMgr playerCacheMgr;


    public Player getPlayer(String channelId) {
        return playerCacheMgr.get(channelId);
    }






}
