package com.wan37.gameServer.event.achievement;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.checker.units.qual.A;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 14:56
 * @version 1.00
 * Description: mmorpg
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PKEvent extends Event {

    private Player player;

    private boolean isWin;

}
