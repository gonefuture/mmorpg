package com.wan37.gameserver.game.sceneObject.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/18 17:09
 * @version 1.00
 * Description: 场景对象的类型
 */
public enum  SceneObjectType {

    /** npc类型*/
    NPC(1),
    /** 野怪类型 */
    WILD_MONSTER(2),
    /** 副本怪物类型 */
    INSTANCE_MONSTER(3),

    /** 召唤兽 **/
    PET(4),
    ;

    Integer type;


    SceneObjectType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
