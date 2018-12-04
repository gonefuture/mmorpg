package com.wan37.gameServer.game.shop.model;

import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 11:05
 * @version 1.00
 * Description: 商店实体
 */

@Data
public class Shop  implements IExcel<Integer> {


    @EntityName(column = "ID")
    private Integer shopId;

    @EntityName(column = "商店名字")
    private String shopName;

    @EntityName(column = "货物")
    private String goods;


    private Map<Integer,Things> goodsMap = new HashMap<>();


    @Override
    public Integer getKey() {
        return this.shopId;
    }
}
