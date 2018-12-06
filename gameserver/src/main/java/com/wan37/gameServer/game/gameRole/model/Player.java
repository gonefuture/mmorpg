package com.wan37.gameServer.game.gameRole.model;


import com.wan37.gameServer.game.bag.model.Bag;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;


import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.model.Creature;
import com.wan37.mysql.pojo.entity.TPlayer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.text.MessageFormat;
import java.util.*;
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
public class Player  extends TPlayer   implements Creature  {

    // 玩家战力
    int attack;


    // 角色当前使用技能的集合
    Map<Integer, Skill> skillMap = new ConcurrentHashMap<>();



    // 角色当前的buffer,因为可能拥有多个重复的技能，所以这里使用List保存
    List<Buffer> bufferList = new CopyOnWriteArrayList<>();


    Map<Integer,RoleProperty> rolePropertyMap = new ConcurrentHashMap<>();


    // 装备
    Map<String,Item> equipmentBar = new ConcurrentHashMap<>();


    // 背包栏

    Bag bag = new Bag(this.getId(),"16格背包",16,1) ;


    // 玩家当前进行的副本。
    GameInstance currentGameInstance;



    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp());
    }

}
