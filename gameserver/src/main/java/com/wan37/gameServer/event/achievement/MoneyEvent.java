package com.wan37.gameServer.event.achievement;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
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
