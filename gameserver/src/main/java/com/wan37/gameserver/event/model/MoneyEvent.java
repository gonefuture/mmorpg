package com.wan37.gameserver.event.model;

import com.wan37.gameserver.event.Event;
import com.wan37.gameserver.game.player.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:26
 * @version 1.00
 * Description: 金币事件
 */


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class MoneyEvent extends Event {

    private Player player;
    private Integer money;
}
