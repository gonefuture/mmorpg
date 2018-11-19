package com.wan37.gameServer.game.scene.servcie;


import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     *  通过id查找场景
     */
    public GameScene findSceneById(int sceneId) {
        return sceneCacheMgr.get(sceneId);
    }

    /**
     *  通过字符串的id序列查找场景
     */
    public List<GameScene> findSceneByIds(String sceneIds) {
        List<GameScene> gameSceneList = new ArrayList<>();
        String[] stringIds = sceneIds.split(",");
        Arrays.stream(stringIds).forEach((stringId) -> {
            Integer id = Integer.valueOf(stringId);
            GameScene gameScene = sceneCacheMgr.get(id);
            gameSceneList.add(gameScene);
        });
        return gameSceneList;
    }





}
