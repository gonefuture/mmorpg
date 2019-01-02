package com.wan37.gameServer.event.achievement;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 11:36
 * @version 1.00
 * Description: mmorpg
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TalkWithEvent extends Event {
    private Player player;
    private Long sceneObjectId;
}
