package com.wan37.gameServer.game.mission.model;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 10:57
 * @version 1.00
 * Description: 任务状态
 */


public enum MissionState {


    NOT_START("任务未接", 1),

    RUNNING("进行中", 2),

    COMPLETE("任务完成", 3),

    FINISH("任务结束", 4),

    // 任务完成了之后不再触发
    NEVER("不再触发",5);

    String name;

    Integer code;

    MissionState(String name, Integer code) {
        this.name = name;
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public static MissionState valueOf(Integer code) {
        switch (code) {
            case 1:
                return NOT_START;
            case 2:
                return RUNNING;
            case 3:
                return COMPLETE;
            case 4:
                return FINISH;
            default:
                return NOT_START;
        }
    }
}