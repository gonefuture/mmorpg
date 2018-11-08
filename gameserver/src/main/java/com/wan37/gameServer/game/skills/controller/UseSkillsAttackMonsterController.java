package com.wan37.gameServer.game.skills.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.EorroMsg;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.entity.Monster;
import com.wan37.gameServer.game.SceneObject.service.GameObjectService;
import com.wan37.gameServer.game.skills.service.UseSkillsService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 12:20
 * @version 1.00
 * Description: 角色使用技能
 */

@Controller
public class UseSkillsAttackMonsterController implements IController {

    @Resource
    private UseSkillsService useSkillsService;

    @Resource
    private GameObjectService gameObjectService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] parameter = new String(message.getContent()).split("\\s+");
        int skillId = Integer.valueOf(parameter[1]);
        long gameObjectId =  Long.valueOf(parameter[2]);

        String result = null;

        Monster monster = useSkillsService.attackMonsterBySkill( ctx.channel().id().asLongText(),
                skillId,
                gameObjectId
        );

        if (monster != null) {
            message.setFlag((byte)1);

            result = JSON.toJSONString(monster);
        } else {
            // 失败标记
            message.setFlag((byte)-1);
            result = JSON.toJSONString(new EorroMsg(500,"角色不能使用技能，角色死亡或者mp不足"));
        }

        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}
