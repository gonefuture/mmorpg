package com.wan37.gameServer.game.friend.model;

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
@NoArgsConstructor
public class Friend {

    private Long playerId;

    private String name;

    private String roleClass;

    private Date LastOnlineTime;

    private boolean isOnline;

}
