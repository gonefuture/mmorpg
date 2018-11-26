package com.wan37.gameServer.game.gameInstance.service;

import com.wan37.gameServer.game.gameInstance.InstanceManager;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/26 16:51
 * @version 1.00
 * Description: 副本服务。
 */

@Slf4j
@Service
public class InstanceService {

    @Resource
    private InstanceManager instanceManager;



    public boolean enterInstance(Player player) {

        GameInstance gameInstance = instanceManager.createInstance(player, player.getScene());
        return gameInstance == null;
    }


}
