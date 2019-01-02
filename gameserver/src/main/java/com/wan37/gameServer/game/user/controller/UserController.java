package com.wan37.gameServer.game.user.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.game.user.service.UserService;
import com.wan37.gameServer.manager.cache.UserCacheMgr;
import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.model.User;
import com.wan37.gameServer.util.ParameterCheckUtil;
import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 10:19
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class UserController  {


    {
        ControllerManager.add(MsgId.USER_LOGIN,this::userLogin);
        ControllerManager.add(MsgId.LIST_GAME_ROLES,this::roleList);
    }


    @Resource
    private UserService userService;

    @Resource
    private UserCacheMgr userCacheMgr;



    public void userLogin(ChannelHandlerContext ctx, Message message) {
        String[] array = ParameterCheckUtil.checkParameter(ctx,message,3);
        long userId =  Long.valueOf(array[1]);
        String password = array[2];
        boolean flag = userService.login(userId, password,
                ctx);
        String result = null;
        if (flag) {
            // 将用户id放入缓存，与channel上下文联系起来
            userCacheMgr.saveCtx(userId,ctx);
            result = "登陆成功,请发送指令 list_roles 加载角色列表";
        } else {
           result = "登陆失败，请检查用户名或密码";
        }
        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }





    public void roleList(ChannelHandlerContext ctx, Message message) {

        User user = userCacheMgr.get(ctx.channel().id().asLongText());
        List<TPlayer> tPlayerList = userService.findPlayers(user.getId());
        StringBuilder sb = new StringBuilder();

        tPlayerList.forEach( tPlayer -> {
            sb.append(tPlayer.toString());
            sb.append("\n");
        });
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
