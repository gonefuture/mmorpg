package com.wan37.gameserver.game.scene.model;

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
    Integer type;


    SceneType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }



}
