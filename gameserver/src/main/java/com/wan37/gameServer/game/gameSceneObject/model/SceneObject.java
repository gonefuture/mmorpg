package com.wan37.gameServer.game.sceneObject.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 23:39.
 *  说明：
 */

import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.model.Creature;
import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <pre> </pre>
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
    private Integer attack;

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


    // 角色当前使用技能的集合
    Map<Integer, Skill> skillMap = new ConcurrentHashMap<>();

    // 角色当前的buffer,因为可能拥有多个重复的技能，所以这里使用List保存
    List<Buffer> bufferList = new CopyOnWriteArrayList<>();


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
