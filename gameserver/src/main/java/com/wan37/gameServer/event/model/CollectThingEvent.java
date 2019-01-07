package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.things.model.Things;
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
    private Things things;
    private Integer count;
}
