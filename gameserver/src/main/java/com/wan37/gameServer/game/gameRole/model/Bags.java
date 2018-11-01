package com.wan37.gameServer.game.gameRole.model;

import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TThings;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 16:54
 * @version 1.00
 * Description: 背包
 */
@Data
public class Bags {

    private Long playerId;

    List<TThings> thingsList = new ArrayList<>();

}
