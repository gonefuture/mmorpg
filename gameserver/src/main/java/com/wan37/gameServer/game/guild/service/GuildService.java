package com.wan37.gameServer.game.guild.service;

import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.bag.service.BagsService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.guild.manager.GuildManager;
import com.wan37.gameServer.game.guild.model.Guild;
import com.wan37.gameServer.game.guild.model.PlayerJoinRequest;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;

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

    @Resource
    private BagsService bagsService;

    @Resource
    private GuildManager guildManager;


    /**
     *  显示公会信息
     * @param ctx 上下文
     */
    public void guildShow(ChannelHandlerContext ctx) {
         Player player = playerDataService.getPlayerByCtx(ctx);
        Guild guild = GuildManager.getGuild(player.getGuildId());
        StringBuilder sb = new StringBuilder();

        if (null == guild) {
            notificationManager.notifyPlayer(player,"您尚未加入公会");
            return;
        }
        sb.append(MessageFormat.format(
                "公会： {0} {1}  等级：{2} \n ",guild.getId(),guild.getName(),guild.getLevel()
        ));

        sb.append(MessageFormat.format("公会成员({0}个)：\n",guild.getMemberMap().size()));

        guild.getMemberMap().forEach(
                (id,p) -> sb.append(MessageFormat.format("{0} {1}   职业：{3} 等级：{2} 公会职位：{3}\n",
                p.getId(),p.getName(),p.getRoleClass(),p.getLevel(),p.getGuildClass()))
        );

        sb.append(MessageFormat.format("公会仓库(容量{0})：\n",guild.getWarehouseSize()));
        if (guild.getWarehouseMap().size() == 0) {
            sb.append("公会仓库为空\n");
        }
        guild.getWarehouseMap().forEach(
                (index,i) -> sb.append(MessageFormat.format("{0} {1}   部位：{2} 价格：{3} \n",
                                index,i.getThings().getName(),i.getThings().getPart(),i.getThings().getPrice()))
        );
        sb.append("入会申请: ");
        guild.getPlayerJoinRequestMap().forEach(
                (id,request) ->  sb.append(MessageFormat.format("玩家 {0} {1} {2} {3}\n",
                                id,request.getPlayer().getName(),request.getDate(),request.isAgree()))
        );
        notificationManager.notifyPlayer(player,sb);

    }

    /**
     *  创建一个公会
     * @param ctx 上下文
     */
    public void guildCreate(ChannelHandlerContext ctx,String guildName) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player.getGuildId() != null  && player.getGuildId() != 0) {
            notificationManager.notifyPlayer(player,"您已经在一个公会之中");
            return;
        }

        Guild guild = new Guild(player.getId().intValue(),guildName,1,30);
        guild.getMemberMap().put(player.getId(),player);
        // 设置创建公会者的职位,3为会长的职务
        player.setGuildId(guild.getId());
        player.setGuildClass(3);
        notificationManager.notifyPlayer(player,"公会创建成功");
        GuildManager.putGuild(guild.getId(),guild);
        guildManager.insertGuild(guild);
    }

    public void guildJoin(ChannelHandlerContext ctx,Integer guildId) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player.getGuildId() != null  && player.getGuildId() != 0) {
            notificationManager.notifyPlayer(player,"您已经在一个公会之中，您要先退出当前公会才能进入新公会");
            return;
        }
        Guild guild = GuildManager.getGuild(guildId);
        if (null == guild) {
            notificationManager.notifyPlayer(player,"该公会并不存在");
            return;
        }
        PlayerJoinRequest playerJoinRequest = new PlayerJoinRequest(false,new Date(),player);
        guild.getPlayerJoinRequestMap().put(player.getId(),playerJoinRequest);
        notificationManager.notifyPlayer(player,"发送入会申请成功");
        guildManager.updateGuild(guild);
    }


    /**
     *  允许入会
     * @param ctx 上下文
     * @param playerId
     */
    public void guildPermit(ChannelHandlerContext ctx, Long playerId) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player.getGuildId() == 0) {
            notificationManager.notifyPlayer(player,"您并没有公会，不能操作");
            return;
        }
        if (player.getGuildClass()<2) {
            notificationManager.notifyPlayer(player,"您的公会职位没有权限允许别人入会");
            return;
        }
        Guild guild = GuildManager.getGuild(player.getGuildId());
        PlayerJoinRequest playerJoinRequest = guild.getPlayerJoinRequestMap().get(playerId);
        if (null == playerJoinRequest) {
            notificationManager.notifyPlayer(player,"该玩家并没有申请加入公会");
            return;
        }
        Player joiner = playerJoinRequest.getPlayer();
        // 变更设置
        guild.getMemberMap().put(joiner.getId(),joiner);
        joiner.setGuildClass(0);
        joiner.setGuildId(guild.getId());
        playerJoinRequest.setAgree(true);
        notificationManager.notifyPlayer(player,MessageFormat.format("已允许玩家{0}加入公会",joiner.getName()));
        notificationManager.notifyPlayer(joiner,MessageFormat.format("你已加入{0}公会",guild.getName()));
        guildManager.updateGuild(guild);
    }


    /**
     *  捐献物品到公会
     * @param ctx 上下文
     * @param bagIndex  玩家背包索引
     */
    public void guildDonate(ChannelHandlerContext ctx,Integer bagIndex) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player.getGuildId() == 0 ) {
            notificationManager.notifyPlayer(player,"您并没有公会，所以不能捐献");
            return;
        }
        Item item = player.getBag().getItemMap().get(bagIndex);
        if (null == item) {
            notificationManager.notifyPlayer(player,"您并没有这件物品");
            return;
        }
        Guild guild = GuildManager.getGuild(player.getGuildId());

        if (guild.warehouseAdd(item)) {
            bagsService.removeItem(player,bagIndex);
            // 展示公会仓库的变化
            guildShow(ctx);
            // 持久化仓库
            guildManager.updateGuild(guild);
        } else  {
            notificationManager.notifyPlayer(player,"捐献失败，可能是公会仓库已满");
        }

    }



    public void guildGrant(ChannelHandlerContext ctx, Long playerId, Integer guildClass) {


    }

    public void guildTake(ChannelHandlerContext ctx, Integer wareHouseIndex) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        if (player.getGuildId() == 0) {
            notificationManager.notifyPlayer(player,"您并没有公会，不能从仓库拿东西");
            return;
        }
        Guild guild = GuildManager.getGuild(player.getGuildId());
        Item item  = guild.warehouseTake(wareHouseIndex);
        if (item == null) {
            notificationManager.notifyPlayer(player,"公会仓库并没有这件物品");
            return;
        }
        if (bagsService.addItem(player,item)) {
            guild.getWarehouseMap().remove(wareHouseIndex);
            guildManager.updateGuild(guild);
        } else {
            notificationManager.notifyPlayer(player,"获取公会物品失败，可能原因是你的背包满了");
        }
    }

    public void guildQuit(ChannelHandlerContext ctx) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        Guild guild = GuildManager.getGuild(player.getGuildId());
        if (null != guild) {
            player.setGuildId(0);
            player.setGuildClass(0);
            guild.getMemberMap().remove(player.getId());
            guildManager.updateGuild(guild);
        }
        notificationManager.notifyPlayer(player,"已退出公会");

    }
}
