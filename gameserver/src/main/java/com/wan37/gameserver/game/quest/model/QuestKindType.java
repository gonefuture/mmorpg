package com.wan37.gameserver.game.quest.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/28 14:29
 * @version 1.00
 * Description: mmorpg
 */
public enum QuestKindType {
    /** 成就 **/
    ACHIEVEMENT(0),

    /** 新手 **/
    NOVICE(1),

    /** 主线 **/
    MAIN(2),


    SIDE(3),

    UNLIMITED(4),

    DAILY(5),

    WEEK(6),

    MONTH(7)

    ;
    private Integer kind;

    QuestKindType(Integer kind) {
        this.kind = kind;
    }

    public Integer getKind() {
        return kind;
    }
}
