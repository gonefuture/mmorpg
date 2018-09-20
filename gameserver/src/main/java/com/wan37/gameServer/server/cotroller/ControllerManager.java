package com.wan37.gameServer.server.cotroller;

import com.wan37.gameServer.common.Controller;
import com.wan37.gameServer.common.MsgId;
import com.wan37.gameServer.controller.HelloController;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 14:57
 * @version 1.00
 * Description: 控制器管理。 通过msgId映射到其关联的映射器处理。
 */

@Component
public class ControllerManager {

    private Map<Integer,Controller>  controllerMap = new HashMap<>();


    ControllerManager(){
        init();
    }


    private void init() {
        controllerMap.put(MsgId.HEllO_1000.getMsgId(),new HelloController());
    }


    public Controller get(int msgId) {
        return controllerMap.get(msgId);
    }
}
