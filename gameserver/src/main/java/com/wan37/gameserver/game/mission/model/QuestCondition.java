package com.wan37.gameserver.game.mission.model;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:37
 * @version 1.00
 * Description: 任务条件
 */
public final class QuestCondition {
    // 第一次类型的任务，只能完成一次
    public static final String FIRST_ACHIEVEMENT = "first";

    // 以目标数量为条件
    public static final String NUMBER = "number";

    //  以特定目标NPC,怪物，物品，副本等id为完成条件
    public static final String ID = "id";

}
