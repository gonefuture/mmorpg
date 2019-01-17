package com.wan37.gameserver.game.sceneObject.model;


import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.skills.model.Pet;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.model.Creature;
import com.wan37.gameserver.model.IExcel;
import com.wan37.gameserver.util.excel.EntityName;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 23:39.
 *  说明： 场景对象，包括怪物,npc,宠物
 */


@Data
public class SceneObject implements IExcel<Long>  {

    @EntityName(column = "id")
    private Long id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "生命值")
    private Long hp;

    @EntityName(column = "魔法值")
    private Long mp;

    @EntityName(column = "攻击力")
    private Long attack;

    @EntityName(column = "交谈文本")
    private String talk = "";

    @EntityName(column = "技能")
    private String skills;

    @EntityName(column = "状态")
    private Integer state;

    @EntityName(column = "角色类型")
    private Integer roleType;

    @EntityName(column = "刷新时间")
    private Long refreshTime;

    @EntityName(column = "掉落")
    private String drop;


    // 死亡时间
    private long deadTime;


    // 当前使用技能的集合
    Map<Integer, Skill> hasUseSkillMap = new ConcurrentHashMap<>();

    // 角色当前的buffer,因为可能拥有多个重复的技能，所以这里使用List保存
    List<Buffer> bufferList = new CopyOnWriteArrayList<>();


    // 当前攻击对象
    private Creature target;

    /** 宠物 **/
    private Pet pet;




    public  Long getDeadTime() {
        return deadTime;
    }
    public  void  setDeadTime(Long deadTime){
        this.deadTime = deadTime;
    }

    @Override
    public Long getKey() {
        return id;
    }
}
