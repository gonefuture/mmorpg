package com.wan37.gameServer.game.mail.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.mail.service.MailService;
import com.wan37.mysql.pojo.entity.TMail;
import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/5 12:00
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class MailListController implements IController {

    @Resource
    private MailService mailService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Player player = playerDataService.getPlayerByCtx(ctx);
        List<TMail> tMailList = mailService.mailList(player);
        StringBuilder sb = new StringBuilder();
        sb.append("====== 邮件列表  =========== \n");
        tMailList.forEach(

                tMail ->{
                    TPlayer sender = playerDataService.findTPlayer(tMail.getSender());
                    sb.append(MessageFormat.format("邮件：{0}  发件人：{1}：{2} 主题：{3}  内容：{4}  附件：{5} \n",
                            tMail.getId(),
                            sender.getId(),
                            Optional.ofNullable(sender.getName()).orElse("玩家角色已注销"),
                            tMail.getSubject(),
                            tMail.getContent(),
                            tMail.getAttachment()));
                }
        );
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);

    }
}
