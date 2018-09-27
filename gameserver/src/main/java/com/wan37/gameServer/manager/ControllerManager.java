package com.wan37.gameServer.manager;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.controller.GameRoleLoginController;
import com.wan37.gameServer.controller.PlayerLoginController;
import com.wan37.gameServer.controller.RoleMoveController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

        log.info("控制器管理器初始化成功");
    }

    @Resource
    private PlayerLoginController playerLoginController;

    @Resource
    private GameRoleLoginController gameRoleLoginController;

    @Resource
    private RoleMoveController roleMoveController;


    // 加载MsgId与控制器之间的关系
    @PostConstruct
    private void init() {
        controllerMapping.put(MsgId.PLAYER_LOGIN.getMsgId(),playerLoginController);
        controllerMapping.put(MsgId.GAME_ROLE_LOGIN.getMsgId(),gameRoleLoginController);
        controllerMapping.put(MsgId.MOVE.getMsgId(), roleMoveController);
    }


    public IController get(int msgId) {
        return controllerMapping.get(msgId);
    }
}
