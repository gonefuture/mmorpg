package com.wan37.gameServer.game.things.modle;

import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:07
 * @version 1.00
 * Description: mmorpg
 */

@Data
public class ThingsExcel {

    @EntityName(id = true)
    private Integer no;

    @EntityName(column="物品编号")
    private Integer  id;

    @EntityName(column="物品名称")
    private String  name;


    @EntityName(column="触发效果")
    private Integer  buffer;

    @EntityName(column="能否绑定")
    private Integer  isBind;


}
