package com.wan37.gameServer.server.cotroller;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.controller.HelloIController;
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

    private Map<Integer,IController>  controllerMap = new HashMap<>();


    ControllerManager(){
        init();
        log.info("控制器管理器初始化成功");
    }


    private void init() {
        controllerMap.put(MsgId.HEllO_1000.getMsgId(),new HelloIController());
    }


    public IController get(int msgId) {
        return controllerMap.get(msgId);
    }
}
