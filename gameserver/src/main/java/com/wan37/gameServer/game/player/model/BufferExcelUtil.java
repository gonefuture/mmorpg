package com.wan37.gameServer.game.player.model;

import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/1 10:09
 * @version 1.00
 * Description: mmorpg
 */
public class BufferExcelUtil extends ReadExcelByEntity<Buffer> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public BufferExcelUtil(String filepath) {
        super(filepath);
    }
}
