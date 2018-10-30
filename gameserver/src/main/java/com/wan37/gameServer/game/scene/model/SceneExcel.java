package com.wan37.gameServer.game.scene.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/30 11:18.
 *  说明：
 */

import com.wan37.gameServer.util.excel.EntityName;
import lombok.Data;

/**
 * <pre> </pre>
 */

@Data
public class SceneExcel {

    @EntityName(column = "ID")
    private Integer id;

    @EntityName(column = "名字")
    private String name;

    @EntityName(column = "状态")
    private String state;

    @EntityName(column = "相邻场景")
    private String neighbors;

    @EntityName(column = "场景对象")
    private String gameObjectIds;

}
