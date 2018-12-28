package com.wan37.gameServer.game.mission.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/28 9:33
 * @version 1.00
 * Description: mmorpg
 */
public enum MissionType {


    KILL_MONSTER(1),
    COLLECT_THINGS(2),
    TALK_WITH(3),
    FIRST_ACHIEVEMENT(4),
    OTHER_ACHIEVEMENT(5),
    MONEY_INCREASE(6)
    ;


    MissionType(Integer typeId) {
        this.typeId = typeId;
    }

    Integer typeId;

    public Integer getTypeId() {
        return typeId;
    }


}
