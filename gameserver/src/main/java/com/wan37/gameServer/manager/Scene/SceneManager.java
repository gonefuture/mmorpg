package com.wan37.gameServer.manager.Scene;

import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.SceneCacheMgr;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.gameServer.service.GameSceneService;

import com.wan37.mysql.pojo.entity.TScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 18:06
 * @version 1.00
 * Description: 场景定时定时器
 */

@Component
@Slf4j
public class SceneManager {

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);

    @Resource
    private SceneCacheMgr sceneCacheMgr;

    @Resource
    private GameSceneService gameSceneService;

    @PostConstruct
    private void init() {
        List<GameScene>  gameSceneList= sceneCacheMgr.list();
        for (GameScene gameScene : gameSceneList) {
            //List<Player> playerList = tScene.getPlayers();
        }
    }









}
