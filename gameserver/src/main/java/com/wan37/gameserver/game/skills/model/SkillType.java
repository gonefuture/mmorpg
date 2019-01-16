package com.wan37.gameserver.game.skills.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 18:59
 * @version 1.00
 * Description: 技能类型
 */
public enum  SkillType {

    /** 对自身或友方使用 */
    FRIENDLY(1),

    /** 对敌人单人使用 **/
    ATTACK_SINGLE(2),

    /** 多人技能 **/
    ATTACK_MULTI(3),

    /** 召唤宠物 */
    CALL_PET(5),

    /** 只能自身使用 */
    ONLY_SELF(6),

    /** 嘲讽技能 **/
    TAUNT(7)



    ;


    SkillType(Integer typeId) {
        this.typeId = typeId;
    }

    private Integer typeId;


    public Integer getTypeId() {
        return typeId;
    }

}
