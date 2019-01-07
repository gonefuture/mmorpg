package com.wan37.gameServer.game.scene.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/7 15:24
 * @version 1.00
 * Description: mmorpg
 */
public enum  SceneType {

    // 普通场景
    COMMON_SCENE(1),

    INSTANCE_SCENE(2),

    BATTLEFIELD(3)



    ;
    Integer code;


    SceneType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }



}
