package com.wan37.gameServer.manager.controller;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.chat.controller.ChatController;
import com.wan37.gameServer.game.mail.controller.MailController;
import com.wan37.gameServer.game.scene.controller.AOIController;
import com.wan37.gameServer.game.scene.controller.SceneController;
import com.wan37.gameServer.game.shop.controller.GoodsController;
import com.wan37.gameServer.game.things.controller.ItemController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final static Map<MsgId,IController>  controllerMapping = new ConcurrentHashMap<>();
    
    public static void add(MsgId msgId, IController controller) {
        controllerMapping.put(msgId, controller);
    }


    public IController get(int msgId) {
        // 通过int的msgId找到枚举的MsgId
        return controllerMapping.get(MsgId.find(msgId,MsgId.UNKNOWN));
    }
}
