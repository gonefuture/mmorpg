package com.wan37.gameserver.event.model;


import com.wan37.gameserver.event.Event;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.mission.model.Mission;
import com.wan37.gameserver.game.mission.model.MissionProgress;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 10:35
 * @version 1.00
 * Description: 任务事件
 */

@Data
@EqualsAndHashCode(callSuper=true)
public class MissionEvent extends Event {
    private Player player;
    private Mission mission;
    private MissionProgress missionProgress;


    public MissionEvent(Player player, Mission mission, MissionProgress missionProgress) {
        this.player = player;
        this.mission = mission;
        this.missionProgress = missionProgress;
    }
}
