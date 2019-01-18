package com.wan37.gameserver.game.user.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.manager.RoleClassManager;
import com.wan37.gameserver.game.user.service.UserService;
import com.wan37.gameserver.game.user.manager.UserCacheManger;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.model.User;
import com.wan37.gameserver.util.ParameterCheckUtil;
import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
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


    @Resource
    private UserService userService;

    @Resource
    private NotificationManager notificationManager;


    {
        ControllerManager.add(MsgId.USER_LOGIN,this::userLogin);
        ControllerManager.add(MsgId.LIST_GAME_ROLES,this::roleList);
        ControllerManager.add(MsgId.USER_CREATE,this::userCreate);
    }

    private void userCreate(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,4);
        String userName = args[1];
        String password = args[2];
        String phone = args[3];
        userService.register(ctx,userName,password,phone);

    }





    private void userLogin(ChannelHandlerContext ctx, Message message) {
        String[] array = ParameterCheckUtil.checkParameter(ctx,message,3);
        long userId =  Long.valueOf(array[1]);
        String password = array[2];
        userService.login(userId,password,ctx);
    }




    private void roleList(ChannelHandlerContext ctx, Message message) {
        User user = UserCacheManger.getUserByCtx(ctx);
        List<TPlayer> list = userService.findPlayers(user.getId());
        StringBuilder sb = new StringBuilder();
        list.forEach(tPlayer -> sb.append(MessageFormat.format("id:{0} 名字：{1} 职业：{2} \n",
                tPlayer.getId(),tPlayer.getName(),
                RoleClassManager.getRoleClass(tPlayer.getRoleClass()).getName())));
        NotificationManager.notifyByCtx(ctx,sb.toString());

    }
}
