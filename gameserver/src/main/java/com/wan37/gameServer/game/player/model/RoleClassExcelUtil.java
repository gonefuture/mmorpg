package com.wan37.gameServer.game.player.model;

import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/21 16:34
 * @version 1.00
 * Description: mmorpg
 */
public class RoleClassExcelUtil  extends ReadExcelByEntity<RoleClass> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public RoleClassExcelUtil(String filepath) {
        super(filepath);
    }
}
