package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 17:48
 * @version 1.00
 * Description: 队伍事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TeamEvent extends Event {

    private List<Player> teammate;


}
