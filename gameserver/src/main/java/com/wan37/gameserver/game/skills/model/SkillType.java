package com.wan37.gameserver.game.skills.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 18:59
 * @version 1.00
 * Description: 技能类型
 */
public enum  SkillType {

    /** 单人技能 **/
    ATTACK_SINGLE(1),

    /** 多人技能 **/
    ATTACK_MULTI(3),


    CALL_PET(5)

    ;


    SkillType(Integer typeId) {
        this.typeId = typeId;
    }

    private Integer typeId;


    public Integer getTypeId() {
        return typeId;
    }

}
