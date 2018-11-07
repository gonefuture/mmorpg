package com.wan37.gameServer.entity;

import com.wan37.gameServer.game.SceneObject.model.SceneObject;
import com.wan37.mysql.pojo.entity.TGameObject;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:26
 * @version 1.00
 * Description: 怪物
 */


@Data
public class Monster  extends SceneObject implements SceneActor {

    // 死亡时间
    private long deadTime;
}
