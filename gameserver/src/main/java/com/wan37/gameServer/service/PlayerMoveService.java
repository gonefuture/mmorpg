package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.gameServer.manager.cache.SceneManager;
import com.wan37.mysql.pojo.entity.TScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 17:32
 * @version 1.00
 * Description: 角色移动服务
 */

@Slf4j
@Service
public class PlayerMoveService {

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private SceneManager sceneManager;


    public boolean moveScene(String channel,int willMoveSceneId) {
        Player player = playerCacheMgr.get(channel);
        TScene tScene = sceneManager.get(player.getScene());
        String[] neighbors = tScene.getNeighbors().split(",");
        for (String sceneId : neighbors) {
            if (String.valueOf(willMoveSceneId).equals(sceneId)) {
                return true;
            }
        }
        return false;
    }


    public TScene currentScene(String channelId) {
        Player player =  playerCacheMgr.get(channelId);
        return sceneManager.get(player.getScene());

    }

    public List<Player> scanObject() {
        return null;
    }



}
