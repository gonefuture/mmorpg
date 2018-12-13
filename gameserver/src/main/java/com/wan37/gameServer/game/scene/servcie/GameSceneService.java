package com.wan37.gameServer.game.scene.servcie;


import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import io.netty.channel.ChannelHandlerContext;
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


    @Resource
    private PlayerDataService playerDataService;

    /**
     *  通过id查找场景
     */
    public GameScene findSceneById(int sceneId) {
        return sceneCacheMgr.get(sceneId);
    }

    /**
     *      通过
     * @param player   玩家
     * @return 空或者场景
     */
    public GameScene findSceneByPlayer(Player player) {
        return sceneCacheMgr.get(player.getScene());
    }

    /**
     *  通过字符串的id序列查找场景
     */
    public List<GameScene> findNeighborsSceneByIds(String sceneIds) {
        List<GameScene> gameSceneList = new ArrayList<>();
        String[] stringIds = sceneIds.split(",");
        Arrays.stream(stringIds).forEach((stringId) -> {
            Integer id = Integer.valueOf(stringId);
            GameScene gameScene = sceneCacheMgr.get(id);
            gameSceneList.add(gameScene);
        });
        return gameSceneList;
    }

    /**
     *  通过玩家寻找相邻场景
     * @param player 玩家
     * @return 相邻的场景
     */
    public List<GameScene> findNeighborsSceneByPlayer(Player player) {
        GameScene gameScene = findSceneById(player.getScene());
        return findNeighborsSceneByIds(gameScene.getNeighbors());
    }

    /**
     *      通过上下文查找场景
     * @param ctx 通道上下文
     * @return 该通道当前的场景
     */
    public GameScene findSceneByCtx(ChannelHandlerContext ctx) {

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        return sceneCacheMgr.get(player.getScene());
    }

    /**
     *  传送进某个场景
     * @param player 玩家
     * @param sceneId 目的场景的id
     */
    public void carryToScene(Player player, int sceneId) {

        GameScene gameScene = findSceneByPlayer(player);

        log.debug("gameScene  {}",gameScene.getPlayers() );
        // 从当前场景移除
        gameScene.getPlayers().remove(player.getId());

        log.debug("after gameScene  {}", gameScene.getPlayers() );
        player.setScene(sceneId);

        GameScene targetScene = findSceneById(sceneId);
        // 放入目的场景
        targetScene.getPlayers().put(player.getId(), player);
    }




}
