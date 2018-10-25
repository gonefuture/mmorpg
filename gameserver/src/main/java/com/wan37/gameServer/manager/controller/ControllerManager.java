package com.wan37.gameServer.manager.controller;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.controller.*;
import com.wan37.gameServer.game.gameRole.controller.PlayerLoginController;
import com.wan37.gameServer.game.gameRole.controller.PlayerQuitController;
import com.wan37.gameServer.game.scene.controller.AOIController;
import com.wan37.gameServer.game.scene.controller.PlayerMoveController;
import com.wan37.gameServer.game.user.controller.UserLoginController;
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
    private UserLoginController userLoginController;

    @Resource
    private PlayerLoginController playerLoginController;

    @Resource
    private PlayerMoveController playerMoveController;

    @Resource
    private ListGameRoleController listGameRoleController;

    @Resource
    private AOIController aoiController;

    @Resource
    private PlayerQuitController playerQuitController;


    @Resource
    private UseSkillsAttackMonsterController useSkillsAttackMonsterController;

    @Resource
    private StartBufferController startBufferController;


    // 加载MsgId与控制器之间的关系
    @PostConstruct
    private void init() {
        add(MsgId.USER_LOGIN.getMsgId(), userLoginController);
        add(MsgId.PLAYER_LOGIN.getMsgId(),playerLoginController);
        add(MsgId.MOVE.getMsgId(), playerMoveController);
        add(MsgId.LIST_GAME_ROLES.getMsgId(),listGameRoleController);
        add(MsgId.AOI.getMsgId(),aoiController);
        add(MsgId.PLAYER_EXIT.getMsgId(),playerQuitController);
        add(MsgId.USE_SKILLS.getMsgId(), useSkillsAttackMonsterController);
        add(MsgId.START_BUFFER.getMsgId(),startBufferController);
    }




    private void add(Integer msgId, IController controller) {
        controllerMapping.put(msgId, controller);
    }


    public IController get(int msgId) {
        return controllerMapping.get(msgId);
    }
}
