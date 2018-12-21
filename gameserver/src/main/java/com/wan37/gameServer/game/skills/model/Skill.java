package com.wan37.gameServer.game.skills.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 21:39.
 *  说明：
 */

import com.wan37.gameServer.model.IExcel;
import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

/**
 * <pre> </pre>
 */
@Data
public class Skill implements IExcel<Integer> {

    @EntityName(column = "id")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "类型")
    private Integer skillsType;

    @EntityName(column = "cd")
    private Long cd;

    @EntityName(column = "消耗mp")
    private Long mpConsumption;

    @EntityName(column = "伤害")
    private Long hpLose;

    @EntityName(column = "等级")
    private Integer level;

    @EntityName(column = "buffer")
    private Integer buffer;

    @EntityName(column = "描述")
    private String   describe;

    private Long activeTime;


    @Override
    public Integer getKey() {
        return id;
    }



}
