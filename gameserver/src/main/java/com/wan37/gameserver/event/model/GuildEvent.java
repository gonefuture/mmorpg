package com.wan37.gameserver.event.model;

import com.wan37.gameserver.event.Event;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.guild.model.Guild;
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
    private Guild guild;
}
