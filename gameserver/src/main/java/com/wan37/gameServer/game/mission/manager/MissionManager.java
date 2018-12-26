package com.wan37.gameServer.game.mission.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.guild.model.Guild;
import com.wan37.gameServer.game.mission.model.Mission;
import com.wan37.gameServer.game.mission.model.MissionExcelUtil;
import com.wan37.gameServer.game.mission.model.MissionProgress;
import com.wan37.gameServer.game.mission.model.MissionState;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.sceneObject.model.SceneObjectExcelUtil;
import com.wan37.gameServer.util.FileUtil;
import com.wan37.mysql.pojo.entity.TMissionProgress;
import com.wan37.mysql.pojo.mapper.TMissionProgressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 18:29
 * @version 1.00
 * Description: 任务成就管理
 */


@Component
@Slf4j
public class MissionManager {

    @Resource
    private TMissionProgressMapper tMissionProgressMapper;

    // 任务成就
    private static Cache<Integer, Mission> missionCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> log.info(notification.getKey() + "任务成就被移除，原因是" + notification.getCause())
            ).build();


    // 玩家任务成就进度
    private static Cache<Long, MissionProgress> missionProgressCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> log.info(notification.getKey() + "玩家任务成就进度被移除，原因是" + notification.getCause())
            ).build();


    public static Mission getMission(Integer missionId) {
        return missionCache.getIfPresent(missionId);
    }


    public static MissionProgress getMissionProgress(Long playerId) {
        return missionProgressCache.getIfPresent(playerId);
    }





    @PostConstruct
    public void init() {
        loadMission();
        loadMissionProgress();
    }

    private void loadMission() {
        String path = FileUtil.getStringPath("gameData/mission.xlsx");
        MissionExcelUtil missionExcelUtil = new MissionExcelUtil(path);
        Map<Integer, Mission> missionMap = missionExcelUtil.getMap();

        missionMap.values().forEach(
                mission -> {
                    mission.getConditionsMap();
                    mission.getRewardThingsMap();
                    missionCache.put(mission.getKey(),mission);}
        );
        log.info("任务成就资源加载完毕");
    }

    private void loadMissionProgress() {
        List<TMissionProgress> tMissionProgressList =  tMissionProgressMapper.selectByExample(null);
        tMissionProgressList.forEach(
                tMissionProgress -> {
                    MissionProgress missionProgress = new MissionProgress(
                            tMissionProgress.getPlayerid(),tMissionProgress.getMissionid(),
                            tMissionProgress.getBegintime(),tMissionProgress.getEndtime()
                    );
                    missionProgress.setMissionState(MissionState.valueOf(tMissionProgress.getMissionState()));

                    // 从数据库获取任务成就进度
                    Map<String, List<Integer>>  missionProgressMap = JSON.parseObject(tMissionProgress.getProgress(),
                            new TypeReference<Map<String,List<Integer>>>(){});
                    log.debug(" missionProgressMap",  missionProgressMap);
                    missionProgress.setProgressMap(missionProgressMap);
                    missionProgressCache.put(missionProgress.getPlayerId(),missionProgress);
                    log.debug("玩家任务成就进度数据数据完毕",  missionProgressMap);

                }
        );
    }










}
