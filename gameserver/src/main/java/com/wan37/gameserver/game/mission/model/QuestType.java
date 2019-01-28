package com.wan37.gameserver.game.mission.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/28 9:33
 * @version 1.00
 * Description: mmorpg
 */
public enum QuestType {


    KILL_MONSTER(1),
    COLLECT_THINGS(2),
    TALK_WITH(3),
    MISSION(4),
    LEVEL(5),
    MONEY(6),
    TEAM(7),
    GUILD(8),
    PK(9),
    INSTANCE(10),
    EQUIPMENT(11),
    TRADE(12),
    FRIEND(13),
    FIRST_ACHIEVEMENT(14),



    ;


    QuestType(Integer typeId) {
        this.typeId = typeId;
    }

    Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }


}
