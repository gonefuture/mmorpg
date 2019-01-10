package com.wan37.gameserver.event.model;

import com.wan37.gameserver.event.Event;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.things.model.ThingInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 12:29
 * @version 1.00
 * Description: 收集物品的事件
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CollectThingEvent extends Event {
    private Player player;
    private ThingInfo thingInfo;
    private Integer count;
}
