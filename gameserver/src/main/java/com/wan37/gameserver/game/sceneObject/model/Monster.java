package com.wan37.gameserver.game.sceneObject.model;

import com.wan37.gameserver.model.Creature;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:26
 * @version 1.00
 * Description: 怪物
 */


@Data
@EqualsAndHashCode(callSuper=true)
public class Monster  extends SceneObject implements Creature {

    // 攻击速率，默认10000毫秒
    private Integer attackSpeed = 10000;
    private long attackTime = System.currentTimeMillis();


    // 当前攻击目标
    Creature target;




    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}  state:{4}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp(),this.getState()  );
    }
}
