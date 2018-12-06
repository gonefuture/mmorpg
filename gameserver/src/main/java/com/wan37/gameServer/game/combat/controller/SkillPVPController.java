package com.wan37.gameServer.game.combat.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 18:18
 * @version 1.00
 * Description: 使用技能进行PVP
 */

@Controller
public class SkillPVPController implements IController {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

    }
}
