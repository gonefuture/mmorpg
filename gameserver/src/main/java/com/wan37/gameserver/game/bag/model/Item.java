package com.wan37.gameserver.game.bag.model;

import com.wan37.gameserver.game.things.model.ThingInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    /** 默认的背包格子是0 */
    private Integer locationIndex = 0;

    private ThingInfo thingInfo;


    private Item() {

    }

    public Item(Long id, Integer count, ThingInfo thingInfo) {
        this.id = id;
        this.count = count;
        this.thingInfo = thingInfo;
    }
}
