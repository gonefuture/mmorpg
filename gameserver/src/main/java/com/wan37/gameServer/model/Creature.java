package com.wan37.gameServer.model;

import lombok.Data;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 15:38
 * @version 1.00
 * Description: 场景内的活物
 */

@Data
public abstract  class Creature  extends SceneActor{

    // 活物的血量
    public abstract Long getHp();
    public abstract void setHp(Long hp);



    // 活物的状态，生存 or 死亡
    public abstract Integer getState();
    public abstract void setState(Integer mp);





}
