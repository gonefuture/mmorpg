package com.wan37.gameServer.game.SceneObject.model;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/6 22:57.
 *  说明：掉落实体类
 */

import lombok.Data;

/**
 * <pre> </pre>
 */

@Data
public class Drop {

    // 掉落概率
    Integer chance;

    // 掉落物品
    Integer thingId;

}
