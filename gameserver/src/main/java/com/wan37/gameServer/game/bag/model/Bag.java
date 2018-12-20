package com.wan37.gameServer.game.bag.model;



import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 16:54
 * @version 1.00
 * Description: 背包
 */
@Data
@Slf4j
public class Bag  {


    private Long playerId;

    private String bagName;

    // 背包容量
    private Integer bagSize;

    private Integer type;




    Map<Integer,Item> itemMap = new ConcurrentSkipListMap<>();


    public Bag(Long playerId, String bagName, Integer bagSize, Integer type) {
        this.playerId = playerId;
        this.bagName = bagName;
        this.bagSize = bagSize;
        this.type = type;
    }

    public Bag(Long playerId, Integer bagSize) {
        this.playerId = playerId;
        this.bagSize = bagSize;
    }






}
