package com.wan37.gameserver.game.roleProperty.model;

import com.wan37.gameserver.model.IExcel;
import com.wan37.gameserver.util.excel.EntityName;
import lombok.Data;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:07
 * @version 1.00
 * Description: mmorpg
 */

@Data
public class RoleProperty implements IExcel<Integer> {

    @EntityName(column = "ID")
    private Integer id;

    @EntityName(column = "属性名")
    private String name;

    @EntityName(column = "标准属性值")
    private Integer value;

    @EntityName(column = "种类")
    private String type;

    @EntityName(column = "属性描述")
    private String describe;


    private Integer thingPropertyValue;



    @Override
    public Integer getKey() {
        return id;
    }
}
