package com.wan37.gameServer.controller;

import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.service.PlayerLoginService;
import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.entity.Player;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 15:50
 * @version 1.00
 * Description: 玩家登陆
 */
@Slf4j
@Component
public class PlayerLoginController implements IController {

    @Resource
    private PlayerLoginService playerLoginService;

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        // 以字符串的形式解析客户端发过来的内容
        String content = new String(message.getContent());
        String[] arry = content.split(" ");
        Long id = Long.valueOf(arry[1]);
        String password = arry[2];
        List<GameRole>  gameRoleList= playerLoginService.login(id,password);

        if ( gameRoleList == null) {
            message.setContent("用户不存在或者用户密码错误".getBytes());
        } else {
            message.setContent(gameRoleList.toString().getBytes());
        }
        log.info("发送回去的信息："+message);
        ctx.writeAndFlush(message);
    }
}
