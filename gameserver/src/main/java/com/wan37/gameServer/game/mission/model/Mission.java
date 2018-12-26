package com.wan37.gameServer.game.mission.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.google.common.base.Strings;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.util.excel.EntityName;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/25 18:24
 * @version 1.00
 * Description: mmorpg
 */

@Data
@Slf4j
public class Mission implements IExcel<Integer>{


    @EntityName(column = "id")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "类型")
    private Integer type;

    @EntityName(column = "等级")
    private Integer level;

    @EntityName(column = "完成条件")
    private String conditions;


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
        if (conditionsMap.isEmpty() && !Strings.isNullOrEmpty(this.conditions)) {
            Map<String,Integer> conditionsMapLoaded = JSON.parseObject(this.conditions,new TypeReference<Map<String, Integer>>(){});
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
