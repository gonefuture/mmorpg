package com.wan37.gameserver.game.things.model;

import com.wan37.gameserver.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:20
 * @version 1.00
 * Description: Excel读取辅助类
 */
public class ThingInfoExcelUtil extends ReadExcelByEntity<ThingInfo> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public ThingInfoExcelUtil(String filepath)  {
        super(filepath);
    }


}
