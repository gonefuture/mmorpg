package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 12:20
 * @version 1.00
 * Description: 角色使用技能
 */

@Controller
public class UseSkillsController implements IController {


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

    }
}
