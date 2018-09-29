package com.wan37.gameServer.service;


import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TScene;
import com.wan37.mysql.pojo.mapper.TSceneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private TSceneMapper tSceneMapper;

    @Resource
    private TGameObject tGameObject;



    public TScene findTScene(int sceneId) {
        return tSceneMapper.selectByPrimaryKey(sceneId);
    }



}
