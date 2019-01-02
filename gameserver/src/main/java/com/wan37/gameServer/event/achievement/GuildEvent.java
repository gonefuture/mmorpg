package com.wan37.gameServer.event.achievement;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 14:55
 * @version 1.00
 * Description: 公会事件
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class GuildEvent  extends Event {
    private Player player;
}
