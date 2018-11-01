package com.wan37.gameServer.game.scene.servcie;


import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 17:09
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Service
public class GameSceneService {

    @Resource
    private SceneCacheMgr sceneCacheMgr;




    public GameScene findTScene(int sceneId) {
        return sceneCacheMgr.get(sceneId);
    }





}
