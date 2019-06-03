package com.wan37.gameserver.game.gameguide.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.gameguide.service.GuideService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/22 16:10
 * @version 1.00
 * Description: 展示所有命令
 */
@Controller
public class CommandListController {

    @Resource
    private GuideService guideService;


    {
        ControllerManager.add(Cmd.SHOW_CMD,this::showAllCommandList);
    }

    private void showAllCommandList(ChannelHandlerContext ctx, Message message) {
        List<String> cmdList = guideService.allCmd();
        NotificationManager.notifyByCtx(ctx,"指令： ");
        cmdList.stream().reduce((s, s2) -> s+"\n"+s2  ).ifPresent(
                all ->  NotificationManager.notifyByCtx(ctx,all)
        );
    }


}
