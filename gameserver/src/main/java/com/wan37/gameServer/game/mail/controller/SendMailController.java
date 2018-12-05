package com.wan37.gameServer.game.mail.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.mail.service.MailService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 12:03
 * @version 1.00
 * Description: 发送邮件
 */
@Controller
public class SendMailController implements IController {

    @Resource
    private MailService mailService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Long targetId = Long.valueOf(cmd[1]);
        String subject = cmd[2];
        String content = cmd[3];
        Integer bagIndex = Integer.valueOf(cmd[4]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = mailService.sendMail(player,targetId,subject,content,bagIndex);
        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);

    }

}
