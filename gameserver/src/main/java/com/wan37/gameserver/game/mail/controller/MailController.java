package com.wan37.gameserver.game.mail.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.mail.service.MailService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
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
 * Description: 邮件接口
 */

@Controller
public class MailController  {

    {
        ControllerManager.add(Cmd.MAIL_LIST,this::mailList);
        ControllerManager.add(Cmd.GET_MAIL,this::getMail);
        ControllerManager.add(Cmd.SEND_MAIL,this::sendMail);
    }


    @Resource
    private MailService mailService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;

    /**
     *  列出邮件
     */
    private void mailList(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        List<TMail> tMailList = mailService.mailList(player);
        StringBuilder sb = new StringBuilder();
        sb.append("====== 邮件列表  =========== \n");
        tMailList.forEach(
                tMail ->{
                    TPlayer sender = playerDataService.findTPlayer(tMail.getSender());
                    sb.append(MessageFormat.format("邮件：{0}  发件人：{1}：{2} 主题：{3}  内容：{4}  附件：{5}  是否已读：{6}\n",
                            tMail.getId(),
                            sender.getId(),
                            Optional.ofNullable(sender.getName()).orElse("玩家角色已注销"),
                            tMail.getSubject(),
                            tMail.getContent(),
                            tMail.getAttachment(),
                            tMail.getHasRead()?"是":"否"));
                }
        );
        notificationManager.notifyPlayer(player,sb.toString());
    }


    private void getMail(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Integer targetMail = Integer.valueOf(cmd[1]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = mailService.getMail(player,targetMail);
        notificationManager.notifyPlayer(player,msg.getMsg());
    }

    private void sendMail(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Long targetId = Long.valueOf(cmd[1]);
        String subject = cmd[2];
        String content = cmd[3];
        Integer bagIndex = Integer.valueOf(cmd[4]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        Msg msg = mailService.playerSendMail(player,targetId,subject,content,bagIndex);
        NotificationManager.notifyByCtx(ctx,msg.getMsg());

    }
}
