package com.wan37.gameserver.game.scene.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/8 16:00
 * @version 1.00
 * Description: 通用场景id，用于角色或不确定场景时传送等特殊情况的地址
 */
public enum CommonSceneId {

    BEGIN_SCENE(1),

    CEMETERY(12)

    ;

    private Integer id;


    CommonSceneId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
