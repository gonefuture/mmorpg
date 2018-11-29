package com.wan37.gameServer.game.bag.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/29 16:49
 * @version 1.00
 * Description: 展示装备
 */
public class ShowEquipmentController implements IController {

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String command = new String(message.getContent());


    }
}
