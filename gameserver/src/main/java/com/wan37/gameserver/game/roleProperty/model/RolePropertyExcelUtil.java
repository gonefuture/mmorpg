package com.wan37.gameserver.game.roleProperty.model;

import com.wan37.gameserver.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:22
 * @version 1.00
 * Description: mmorpg
 */
public class RolePropertyExcelUtil extends ReadExcelByEntity<RoleProperty> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public RolePropertyExcelUtil(String filepath) {
        super(filepath);
    }
}
