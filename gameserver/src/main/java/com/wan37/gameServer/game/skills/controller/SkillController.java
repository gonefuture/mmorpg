package com.wan37.gameServer.game.skills.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 16:30
 * @version 1.00
 * Description: 技能控制器
 */

@Controller
public class SkillController {


    @Resource
    private SkillsService skillsService;


    {
        ControllerManager.add(MsgId.USE_SKILL,this::useSkill);
    }

    /**
     *  自身使用技能
     */
    private void useSkill(ChannelHandlerContext cxt, Message message) {
        String[] args =ParameterCheckUtil.checkParameter(cxt,message,1);
        Integer skilId = Integer.valueOf(args[1]);
        skillsService.useSkillSelf(cxt,skilId);

    }


}
