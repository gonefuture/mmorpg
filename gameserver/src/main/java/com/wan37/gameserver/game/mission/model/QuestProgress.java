package com.wan37.gameserver.game.mission.model;

import com.wan37.mysql.pojo.entity.TMissionProgress;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 10:26
 * @version 1.00
 * Description: 任务成就进度
 */

@Data
@EqualsAndHashCode(callSuper=true)
public class QuestProgress extends TMissionProgress {



    private Quest quest;


    // 进度
    Map<String, ProgressNumber> progressMap = new ConcurrentHashMap<>();








}
