package com.wan37.gameserver.game.friend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/4 9:53
 * @version 1.00
 * Description: mmorpg
 */


@Data
@AllArgsConstructor

public class Friend {

    private Long playerId;

    private String name;

    private String roleClass;

    private Date lastOnlineTime;

    private boolean isOnline;


    public Friend() {
    }

    public Friend(Long playerId, String name, String roleClass, Date lastOnlineTime) {
        this.playerId = playerId;
        this.name = name;
        this.roleClass = roleClass;
        this.lastOnlineTime = lastOnlineTime;
    }


}
