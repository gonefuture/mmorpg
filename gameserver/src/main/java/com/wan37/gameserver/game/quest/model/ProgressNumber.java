package com.wan37.gameserver.game.quest.model;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/28 16:47
 * @version 1.00
 * Description: 进行数目
 */

@Data
public class ProgressNumber {

    AtomicInteger now = new AtomicInteger(0);
    Integer goal = 1;



    public ProgressNumber(){

    }

    public ProgressNumber(Integer goal) {
        this.goal = goal;
    }


}
