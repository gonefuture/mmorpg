package com.wan37.gameServer.game.gameRole.model;


import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.LevelEvent;
import com.wan37.gameServer.event.model.MoneyEvent;
import com.wan37.gameServer.game.bag.model.Bag;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;


import com.wan37.gameServer.game.skills.model.Skill;

import com.wan37.gameServer.model.Creature;
import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper=true)
@Slf4j
public class Player extends TPlayer   implements Creature  {


    // 当前通道上下文
    private ChannelHandlerContext ctx;

    // 玩家战力
    private int attack;

    // 受职业配置表和装备影响
    private Long hp;
    private Long mp;


    // 等级，根据经验计算得出
    private Integer level;

    // 角色当前使用技能的集合
    private Map<Integer, Skill> hasUseSkillMap = new ConcurrentHashMap<>();



    // 角色当前的buffer,因为可能拥有多个重复的技能，所以这里使用List保存
    private List<Buffer> bufferList = new CopyOnWriteArrayList<>();


    private Map<Integer,RoleProperty> rolePropertyMap = new ConcurrentHashMap<>();


    // 装备
    private Map<String,Item> equipmentBar = new ConcurrentHashMap<>();


    // 背包栏

    private Bag bag = new Bag(this.getId(),"16格背包",16,1) ;


    // 玩家当前进行的副本。
    private GameInstance currentGameInstance;


    // 玩家当前的队伍id
    private String teamId = "";


    /**
     *  经验增加
     * @param exp 经验
     */
    public void addExp(Integer exp) {
        this.setExp(this.getExp()+exp);
        log.debug("this.getExp()",this.getExp());

        int newLevel = this.getExp()/100;

        // 如果等级发生变化，抛出等级事件
        if (newLevel!= this.getLevel()) {
            EventBus.publish(new LevelEvent(this,newLevel));
        }
        this.setLevel(newLevel);
    }



    /**
     *  金币变化
     * @param money 当前要变化的金币数量，正数为增加，负数减少
     */
    public void moneyChange(Integer money) {
        this.setMoney(this.getMoney()+money);
        EventBus.publish(new MoneyEvent(this,money));
    }




    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp());
    }

}
