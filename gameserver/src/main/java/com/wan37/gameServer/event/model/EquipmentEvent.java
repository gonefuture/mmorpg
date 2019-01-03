package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.model.Things;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 15:51
 * @version 1.00
 * Description: 穿戴武器事件
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class EquipmentEvent extends Event {

    private Player player;
    Things things;
}
