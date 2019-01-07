package com.wan37.gameServer.event.model;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.game.player.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:06
 * @version 1.00
 * Description: 交易事件
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TradeEvent extends Event {

    private Player initiator ;
    private Player accepter ;
}
