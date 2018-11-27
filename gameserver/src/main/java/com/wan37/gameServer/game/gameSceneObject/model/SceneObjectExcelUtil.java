package com.wan37.gameServer.game.gameSceneObject.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/31 10:51.
 *  说明：
 */


import com.wan37.gameServer.util.excel.ReadExcelByEntity;

/**
 * <pre> </pre>
 */
public class SceneObjectExcelUtil extends ReadExcelByEntity<com.wan37.gameServer.game.sceneObject.model.SceneObject> {
    /**
     * 构造工具类
     *
     * @param filepath
     */
    public SceneObjectExcelUtil(String filepath) {
        super(filepath);
    }
}
