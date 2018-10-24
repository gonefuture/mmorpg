package com.wan37.gameServer.game.things.modle;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/23 12:30
 * @version 1.00
 * Description: mmorpg
 */
@Data
public class Things extends ThingsExcel{

    // 物品状态，1为已装备，2为未装备
    private Integer state;
}
