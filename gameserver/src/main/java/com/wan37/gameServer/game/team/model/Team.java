package com.wan37.gameServer.game.team.model;

import com.wan37.gameServer.game.player.model.Player;
import lombok.Data;

import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 12:08
 * @version 1.00
 * Description: 队伍数据结构
 */

@Data
public class Team {

    private String id;

    // 队长id
    private Long captainId;

    private Map<Long, Player> teamPlayer;

    // 小队默认是五人一队
    private Integer teamSize = 5;


    public Team(String id, Map<Long, Player> teamPlayer) {
        this.id = id;
        this.teamPlayer = teamPlayer;
    }
}
