package com.wan37.gameServer.entity;

import com.wan37.mysql.pojo.entity.TBuffer;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.entity.TSkill;
import lombok.Data;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 9:46
 * @version 1.00
 * Description: 玩家
 */

@Data
public class Player extends TPlayer {

    // 角色当前使用技能的集合
    Map<Integer, TSkill> skillMap = new ConcurrentHashMap<>();

    // 角色当前的buffer
    Map<Integer, TBuffer> bufferMap = new ConcurrentHashMap<>();




}
