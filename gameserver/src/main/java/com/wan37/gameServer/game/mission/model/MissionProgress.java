package com.wan37.gameServer.game.mission.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 10:26
 * @version 1.00
 * Description: 任务成就进度
 */

@Data
public class MissionProgress {

    private Long playerId;


    private Integer missionId;

    private Mission mission;

    private MissionState missionState = MissionState.NOT_START;

    private Date beginTime;

    private Date endTime;

    // 进度
    Map<String, List<Integer>> progressMap;

    public MissionProgress(Long playerId, Integer missionId,  Date beginTime, Date endTime) {
        this.playerId = playerId;
        this.missionId = missionId;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
