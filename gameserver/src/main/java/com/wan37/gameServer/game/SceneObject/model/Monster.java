package com.wan37.gameServer.game.SceneObject.model;

import com.wan37.gameServer.model.SceneActor;
import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:26
 * @version 1.00
 * Description: 怪物
 */


@Data
public class Monster  extends SceneObject  {

    // 死亡时间
    private long deadTime;
}
