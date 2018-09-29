package com.wan37.gameServer.entity;

import com.wan37.mysql.pojo.entity.TScene;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:13
 * @version 1.00
 * Description: 场景
 */

@Slf4j
public class GameScene  extends TScene {

    // 相邻场景
    private List<Player> neighbors;

    // 处于场景的NPC
    private List<NPC> NPCs;

    // 处于场景的怪物
    private Monster monsters;



}
