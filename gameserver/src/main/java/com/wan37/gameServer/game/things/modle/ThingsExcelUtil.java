package com.wan37.gameServer.game.things.modle;

import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:20
 * @version 1.00
 * Description: Excel读取辅助类
 */
public class ThingsExcelUtil extends ReadExcelByEntity<ThingsExcel> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public ThingsExcelUtil(String filepath)  {
        super(filepath);
    }


}
