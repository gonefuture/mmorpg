package com.wan37.gameserver.game.scene.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/29 14:56.
 *  说明：
 */

import com.wan37.gameserver.util.excel.ReadExcelByEntity;

/**
 * <pre> </pre>
 */
public class SceneExcelUtil extends ReadExcelByEntity<GameScene> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public SceneExcelUtil(String filepath) {
        super(filepath);
    }
}
