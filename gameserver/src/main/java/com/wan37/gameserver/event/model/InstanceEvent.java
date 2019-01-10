package com.wan37.gameserver.event.model;

import com.wan37.gameserver.event.Event;
import com.wan37.gameserver.game.gameInstance.model.GameInstance;
import com.wan37.gameserver.game.player.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 15:00
 * @version 1.00
 * Description: 副本事件
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InstanceEvent extends Event {
    Player player;
    GameInstance gameInstance;

}
