package com.wan37.gameServer.game.guild.service;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.guild.manager.GuildManager;
import com.wan37.gameServer.game.guild.model.Guild;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/19 17:18
 * @version 1.00
 * Description: 公会服务
 */

@Service
public class GuildService {


    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    /**
     *  显示公会信息
     * @param ctx 上下文
     */
    public void guildShow(ChannelHandlerContext ctx) {
         Player player = playerDataService.getPlayerByCtx(ctx);
        Guild guild = GuildManager.getGuild(player.getGuild());
        if (null == guild) {
            notificationManager.notifyPlayer(player,"您尚未加入公会");
            return;
        }
        notificationManager.notifyPlayer(player, MessageFormat.format(
                "公会： {0} {1}  等级：{2} \n ",guild.getId(),guild.getName(),guild.getLevel()
        ));

        notificationManager.notifyPlayer(player,"公会成员：");







    }

    public void guildCreate(ChannelHandlerContext ctx) {
    }

    public void guildJoin(ChannelHandlerContext ctx) {
    }

    public void guildDonate(ChannelHandlerContext ctx) {
    }

    public void guildGrant(ChannelHandlerContext ctx) {
    }
}
