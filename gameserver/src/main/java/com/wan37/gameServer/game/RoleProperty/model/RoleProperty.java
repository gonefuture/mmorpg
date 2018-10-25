package com.wan37.gameServer.game.RoleProperty.model;

import com.wan37.gameServer.entity.IExcel;
import com.wan37.gameServer.util.excel.EntityName;
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

    @EntityName(column = "属性值")
    private Integer value;

    @EntityName(column = "类型")
    private String type;


    @Override
    public Integer getKey() {
        return id;
    }
}
