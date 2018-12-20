package com.wan37.gameServer.game.guild.model;

import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.mysql.pojo.entity.TGuild;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/20 9:49
 * @version 1.00
 * Description: 工会实体
 */

@Data
public class Guild  extends TGuild{


    // 成员
    private Map<Long, Player> memberMap = new ConcurrentHashMap<>();

    // 公会仓库
    private Map<Integer, Item> warehouseMap = new ConcurrentHashMap<>();







}
