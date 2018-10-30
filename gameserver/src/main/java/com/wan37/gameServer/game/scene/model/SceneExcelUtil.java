package com.wan37.gameServer.game.scene.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/29 14:56.
 *  说明：
 */

import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * <pre> </pre>
 */
public class SceneExcelUtil extends ReadExcelByEntity<SceneExcel> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public SceneExcelUtil(String filepath) {
        super(filepath);
    }
}
