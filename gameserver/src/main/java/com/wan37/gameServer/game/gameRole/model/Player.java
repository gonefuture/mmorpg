package com.wan37.gameServer.game.gameRole.model;


import com.wan37.gameServer.entity.Buffer;
import com.wan37.gameServer.entity.SceneActor;
import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.things.modle.Equipment;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.entity.TSkill;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 9:46
 * @version 1.00
 * Description: 玩家
 */

@Data
@Slf4j
public class Player extends TPlayer  implements SceneActor {


    // 角色当前使用技能的集合
    Map<Integer, TSkill> skillMap = new ConcurrentHashMap<>();

    // 角色当前的buffer,因为可能拥有多个重复的技能，所以这里使用List保存
    List<Buffer> bufferList = new CopyOnWriteArrayList<>();

    // 属性
    List<RoleProperty> rolePropertyList = new ArrayList<>();


    // 物品
    List<Things> bags = new ArrayList<>();


    // 装备
    List<Equipment> equipmentList = new ArrayList<>();



}
