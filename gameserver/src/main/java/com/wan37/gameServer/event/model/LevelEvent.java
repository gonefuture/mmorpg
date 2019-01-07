package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.player.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 14:59
 * @version 1.00
 * Description: mmorpg
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class LevelEvent extends Event {

    private Player player;

    private Integer level;
}
