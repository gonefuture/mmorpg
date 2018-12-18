package com.wan37.gameServer.game.bag.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/28 15:05
 * @version 1.00
 * Description: 整理背包
 */


@Controller
public class PackBagController implements IController {
    {
        ControllerManager.add(MsgId.PACK_BAG,this);
    }

    @Resource
    private BagsService bagsService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        bagsService.packBag(ctx);
    }
}
