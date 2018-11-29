package com.wan37.gameServer.game.bag.model;



import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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




    Map<Integer,Item> itemMap = new LinkedHashMap<>();



    public Bag(Long playerId,Integer bagSize) {
        this.playerId = playerId;
        this.bagSize = bagSize;
    }

    /**
     *  在背包里寻求空位放物品
     */
    public boolean put(Item item) {
        if (item == null)
            return false;
        // 遍历背包所有格子，如果是空格，将物品放入格子
        for (int locationIndex=1; locationIndex <= bagSize; locationIndex++) {
            if (null == itemMap.putIfAbsent(locationIndex,item)) {
                log.debug("此时的背包物品列表 {}",itemMap);
                return true;
            }
        }
        // 如果背包没有空位，物品放入失败
        return false;
    }




}
