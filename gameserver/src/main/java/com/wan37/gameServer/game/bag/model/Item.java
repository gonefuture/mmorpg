package com.wan37.gameServer.game.bag.model;

import com.wan37.gameServer.game.things.model.Things;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/28 15:08
 * @version 1.00
 * Description: 游戏物品
 */

@Data
public class Item {

    private Long id;

    private Integer count;

    private Integer locationIndex;

    private Things things;


}
