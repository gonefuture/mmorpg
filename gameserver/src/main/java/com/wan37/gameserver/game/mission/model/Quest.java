package com.wan37.gameserver.game.mission.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.google.common.base.Strings;
import com.wan37.gameserver.model.IExcel;
import com.wan37.gameserver.util.excel.EntityName;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 18:24
 * @version 1.00
 * Description: mmorpg
 */

@Data
@Slf4j
public class Quest implements IExcel<Integer>{


    @EntityName(column = "id")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "类型")
    private Integer type;

    /** 成就，主线，日常，周常，月常**/
    @EntityName(column = "种类")
    private Integer kind;


    @EntityName(column = "等级")
    private Integer level;


    @EntityName(column = "接受条件")
    private String acceptConditions;


    @EntityName(column = "完成条件")
    private String completionConditions;


    @EntityName(column = "描述")
    private String describe;

    @EntityName(column = "奖励金币")
    private Integer rewardMoney;

    @EntityName(column = "奖励物品装备")
    private String rewardThings;



    private Map<String,Integer> conditionsMap = new HashMap<>();

    private Map<Integer,Integer> rewardThingsMap = new HashMap<>();


    @Override
    public Integer getKey() {
        return id;
    }





    /**
     *  加载完成条件
     */
    public  Map<String,Integer> getConditionsMap() {
        if (conditionsMap.isEmpty() && !Strings.isNullOrEmpty(this.completionConditions)) {
            Map<String,Integer> conditionsMapLoaded = JSON.parseObject(this.completionConditions,new TypeReference<Map<String, Integer>>(){});
            log.debug("conditionsMap {}",conditionsMapLoaded);
            this.conditionsMap = conditionsMapLoaded;
        }
        return  this.conditionsMap;
    }


    /**
     *      加载任务成就奖励的物品装备，键为物品id，值为数量
     * @return 一个map
     */
    public Map<Integer,Integer>  getRewardThingsMap() {
        if (rewardThingsMap.isEmpty()  && !Strings.isNullOrEmpty(this.rewardThings)) {
            Map<Integer,Integer> rewardItemMap = JSON.parseObject(this.rewardThings,new TypeReference<Map<Integer,Integer>>(){});
            rewardItemMap.forEach(
                    (k,v) -> {
                        Integer thingId = Integer.valueOf(k);
                        rewardItemMap.put(thingId,v);
                    }
            );
        }
        return this.rewardThingsMap;
    }


}
