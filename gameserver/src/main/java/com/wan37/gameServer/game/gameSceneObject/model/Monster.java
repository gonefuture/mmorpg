package com.wan37.gameServer.game.gameSceneObject.model;

import com.wan37.gameServer.model.Creature;
import lombok.Data;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 16:26
 * @version 1.00
 * Description: 怪物
 */


@Data
public class Monster  extends SceneObject implements Creature {

    // 攻击速率，默认10000毫秒
    private Integer attackSpeed = 10000;
    private long attackTime = System.currentTimeMillis();




    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}  state:{4}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp(),this.getState()  );
    }
}
