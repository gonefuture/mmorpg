package com.wan37.gameServer.manager;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.controller.GameRoleLoginController;
import com.wan37.gameServer.controller.HelloIController;
import com.wan37.gameServer.controller.PlayerLoginController;
import com.wan37.gameServer.controller.RoleMoveCotroller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 14:57
 * @version 1.00
 * Description: 控制器管理。 通过msgId映射到其关联的映射器处理。
 */

@Component
@Slf4j
public class ControllerManager {

    // MsgId标志和服务之间的映射
    private Map<Integer,IController>  controllerMapping = new HashMap<>();


    ControllerManager(){
        init();
        log.info("控制器管理器初始化成功");
    }


    private void init() {
        controllerMapping.put(MsgId.PLAYER_LOGIN.getMsgId(),new PlayerLoginController());
        controllerMapping.put(MsgId.GAME_ROLE_LOGIN.getMsgId(),new GameRoleLoginController());
        controllerMapping.put(MsgId.MOVE.getMsgId(), new RoleMoveCotroller());
    }


    public IController get(int msgId) {
        return controllerMapping.get(msgId);
    }
}
