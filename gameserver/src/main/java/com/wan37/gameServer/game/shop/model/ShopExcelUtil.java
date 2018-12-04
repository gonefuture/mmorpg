package com.wan37.gameServer.game.shop.model;

import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 11:24
 * @version 1.00
 * Description: mmorpg
 */
public class ShopExcelUtil extends ReadExcelByEntity<Shop> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public ShopExcelUtil(String filepath) {
        super(filepath);
    }
}
