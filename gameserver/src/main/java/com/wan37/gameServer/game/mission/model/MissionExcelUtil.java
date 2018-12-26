package com.wan37.gameServer.game.mission.model;

import com.wan37.gameServer.util.excel.ExcelUtil;
import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 18:25
 * @version 1.00
 * Description: mmorpg
 */
public class MissionExcelUtil extends ReadExcelByEntity<Mission> {
    /**
     * 构造工具类
     *
     * @param filepath 文件路径
     */
    public MissionExcelUtil(String filepath) {
        super(filepath);
    }
}
