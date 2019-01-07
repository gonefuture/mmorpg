package com.wan37.gameServer.game.friend.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.FriendEvent;
import com.wan37.gameServer.game.friend.model.Friend;
import com.wan37.gameServer.game.player.manager.RoleClassManager;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 18:25
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class FriendService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private RoleClassManager roleClassManager;


    /**
     *   朋友列表
     * @param player 玩家
     */
    public void friendList(Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append("好友列表：\n");
        player.getFriendMap().values()
                .forEach(
                        friend -> {
                            friend.setOnline(playerDataService.getOnlinePlayerById(friend.getPlayerId()) == null);
                            friend.setLastOnlineTime(player.getLastOnlineTime());
                            sb.append(MessageFormat.format("id:{0}  名字：{1} 职业：{2} 是否在线：{3}  最近的上线时间{4} \n",
                                    friend.getPlayerId(),friend.getName(),friend.getRoleClass(),
                                    friend.isOnline(),friend.getLastOnlineTime()));
                        }
                );
        if (player.getFriendMap().isEmpty()){
            sb.append("好友列表为空\n");
        }
        notificationManager.notifyPlayer(player,sb.toString());
    }

    /**
     *  添加好友
     * @param player 发起者
     * @param friendId 被添加的玩家id
     */
    public void friendAdd(Player player, Long friendId) {
        Player beAdded = playerDataService.getPlayerById(friendId);

        Optional.ofNullable(beAdded).map(
                b -> {
                    Friend friend = new Friend(
                            b.getId(),
                            b.getName(),
                            RoleClassManager.getRoleClass(b.getRoleClass()).getName(),
                            new Date(),
                            b.getCtx() == null
                    );
                    player.getFriendMap()
                            .put(friend.getPlayerId(),friend);
                    player.setFriends(JSON.toJSONString(player.getFriendMap()));
                    notificationManager.notifyPlayer(player,MessageFormat.format("添加好友{0}成功",b.getName()));
                    return friend;
                }
        ).orElseGet(() ->  {notificationManager.notifyPlayer(player,"添加的玩家不存在"); return null;});


        EventBus.publish(new FriendEvent(player));
    }
}
