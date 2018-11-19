package com.wan37.gameServer.game.SceneObject.model;

import com.wan37.gameServer.model.SceneActor;
import lombok.Data;

import java.text.MessageFormat;

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




    public String displayData() {
        return MessageFormat.format("id:{0}  name:{1}  hp:{2}  mp:{3}"
                ,this.getId(),this.getName(), this.getHp(), this.getMp());
    }
}
