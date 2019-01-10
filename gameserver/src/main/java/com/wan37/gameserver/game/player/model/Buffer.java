package com.wan37.gameserver.game.player.model;

import com.wan37.gameserver.model.IExcel;
import com.wan37.gameserver.util.excel.EntityName;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/12 17:02
 * @version 1.00
 * Description:  状态实体类
 */

@Data
public class Buffer implements IExcel<Integer> {

    @EntityName(column = "id")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "类型")
    private Integer type;

    @EntityName(column = "能否覆盖")
    private Integer cover;

    @EntityName(column = "hp效果")
    private Integer hp;

    @EntityName(column = "mp效果")
    private Integer mp;

    @EntityName(column = "效果")
    private Integer effect;

    @EntityName(column = "持续时间")
    private Long duration;

    @EntityName(column = "间隔时间")
    private Integer intervalTime;

    @EntityName(column = "次数")
    private Integer times;



    private long startTime;


    @Override
    public Integer getKey() {
        return null;
    }
}
