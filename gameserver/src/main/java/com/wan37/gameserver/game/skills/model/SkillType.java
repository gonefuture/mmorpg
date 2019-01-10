package com.wan37.gameserver.game.skills.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 18:59
 * @version 1.00
 * Description: mmorpg
 */
public enum  SkillType {

    ATTACK_SINGLE(1),

    ATTACK_MULTI(3),

    ;


    SkillType(Integer typeId) {
        this.typeId = typeId;
    }

    private Integer typeId;


    public Integer getTypeId() {
        return typeId;
    }

}
